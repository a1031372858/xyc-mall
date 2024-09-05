package org.xyc.app.open.base;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xyc.domain.base.util.Assert;
import org.xyc.domain.order.facade.OrderWriteFacade;
import org.xyc.domain.order.model.request.OrderCreateOrUpdateRequest;
import org.xyc.domain.order.model.request.OrderCreateRequest;
import org.xyc.domain.order.model.to.OrderTO;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xuyachang
 * @date 2024/9/1
 */
@Component
public abstract class AbstractTransferHandler<S> implements BaseTransferHandler{

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private OrderWriteFacade orderWriteFacade;

    protected abstract S getPlatformOrder(String json);

    protected abstract String getUniqueKey(S order);

    protected abstract Long getUpdateTime(S order);

    public abstract OrderTO convert(S data);


    protected abstract String getSuccessResult(OrderTO data);

    protected abstract String getFailResult(OrderTO data);

    @Override
    public String handle(String json){
        RLock lock = null;
        try{
            //1.获取平台订单
            S platformOrder = getPlatformOrder(json);

            String orderCode = getUniqueKey(platformOrder);
            Long curUpdateTime = getUpdateTime(platformOrder);

            //按订单号加锁，保证存储幂等key时没有其他相同请求进来
            lock = redissonClient.getLock(orderCode);
            lock.lock(60, TimeUnit.SECONDS);

            OrderTO data = convert(platformOrder);
            /*
             * 幂等校验key，根据订单号+更新时间，确定为订单该状态的一次请求
             * 如果出现重复，说明此次更新已经处理过，无需重复处理
             */
            String redisKey = orderCode+"_"+curUpdateTime;
            Object redisValue = redissonClient.getBucket(redisKey).get();
            //如果有值则检查更新时间
            if(Objects.nonNull(redisValue)){
                Long lastUpdateTime = Long.valueOf(redisValue.toString());
                //只处理晚于上次更新时间的请求
                if(curUpdateTime<=lastUpdateTime){
                    return getSuccessResult(data);
                }
            }
            Boolean saveResult = saveOrder(data);
            if(Boolean.TRUE.equals(saveResult)){
                //处理完成，将幂等key保存至Redis
                redissonClient.getBucket(redisKey).set(curUpdateTime,3600*24,TimeUnit.SECONDS);
                return getSuccessResult(data);
            }else{
                return getFailResult(data);
            }
        }finally {
            if(Objects.nonNull(lock)){
                lock.unlock();
            }
        }
    }


    private Boolean saveOrder(OrderTO data){
        OrderCreateOrUpdateRequest orderCreateOrUpdateRequest = new OrderCreateOrUpdateRequest();
        orderCreateOrUpdateRequest.setOrderTO(data);
        return Assert.getResult(orderWriteFacade.orderCreateOrUpdate(orderCreateOrUpdateRequest));
    }
}
