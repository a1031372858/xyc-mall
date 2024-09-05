package org.xyc.app.open.handler;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.xyc.app.open.annotation.ChannelApi;
import org.xyc.app.open.base.AbstractTransferHandler;
import org.xyc.app.open.model.MeituanOrderTO;
import org.xyc.app.open.model.TiktokOrderTO;
import org.xyc.domain.order.common.constant.ThirdPartyChannelConstant;
import org.xyc.domain.order.model.to.OrderTO;

/**
 * @author xuyachang
 * @date 2024/9/3
 */
@ChannelApi(channelCode = ThirdPartyChannelConstant.MEITUAN)
@Component
@RequiredArgsConstructor
public class MeituanOrderTransferHandler extends AbstractTransferHandler<MeituanOrderTO> {
    @Override
    protected MeituanOrderTO getPlatformOrder(String json) {
        return JSON.parseObject(json,MeituanOrderTO.class);
    }

    @Override
    protected String getUniqueKey(MeituanOrderTO order) {
        return order.getOrderCode();
    }

    @Override
    protected Long getUpdateTime(MeituanOrderTO order) {
        return order.getUpdateTime();
    }

    @Override
    public OrderTO convert(MeituanOrderTO data) {
        OrderTO order = new OrderTO();
        order.setThirdPartyOrderCode(data.getOrderCode());
        order.setThirdPartyChannelCode(ThirdPartyChannelConstant.MEITUAN);
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
}
