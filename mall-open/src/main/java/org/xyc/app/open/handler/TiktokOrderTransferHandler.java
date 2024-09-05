package org.xyc.app.open.handler;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.xyc.app.open.annotation.ChannelApi;
import org.xyc.app.open.base.AbstractTransferHandler;
import org.xyc.app.open.model.TiktokOrderTO;
import org.xyc.domain.order.common.constant.OrderStatusConstant;
import org.xyc.domain.order.common.constant.ThirdPartyChannelConstant;
import org.xyc.domain.order.facade.OrderWriteFacade;
import org.xyc.domain.order.model.to.OrderTO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuyachang
 * @date 2024/9/2
 */
@ChannelApi(channelCode = ThirdPartyChannelConstant.TIKTOK)
@Component
@RequiredArgsConstructor
public class TiktokOrderTransferHandler extends AbstractTransferHandler<TiktokOrderTO> {

    @Override
    protected TiktokOrderTO getPlatformOrder(String json) {
        return JSON.parseObject(json, TiktokOrderTO.class);
    }

    @Override
    protected String getUniqueKey(TiktokOrderTO order) {
        return order.getOrderCode();
    }

    @Override
    protected Long getUpdateTime(TiktokOrderTO order) {
        return order.getUpdateTime();
    }

    @Override
    public OrderTO convert(TiktokOrderTO data) {
        OrderTO order = new OrderTO();
        order.setThirdPartyOrderCode(data.getOrderCode());
        order.setThirdPartyChannelCode(ThirdPartyChannelConstant.TIKTOK);
        order.setOrderStatus(data.getOrderStatus());
        order.setBuyerId(data.getBuyerId());
        order.setBuyerName(data.getBuyerName());
        order.setBuyerMobile(data.getBuyerMobile());
        order.setPaidAmt(data.getPaidAmt());
        order.setShopId(data.getShopId());
        order.setShopName(data.getShopName());
        return order;
    }

    @Override
    protected String getSuccessResult(OrderTO data) {
        return "成功";
    }

    @Override
    protected String getFailResult(OrderTO data) {
        return "失败";
    }

    public static void main(String[] args) {
        TiktokOrderTO order = new TiktokOrderTO();
        order.setOrderCode("tiktok_20240904");
        order.setOrderStatus(OrderStatusConstant.PAID);
        order.setBuyerId(1L);
        order.setBuyerName("张三");
        order.setBuyerMobile("15797704512");
        order.setPaidAmt(new BigDecimal(100));
        order.setShopId(1L);
        order.setShopName("测试店铺");
        String json = JSON.toJSONString(order);
    }
}
