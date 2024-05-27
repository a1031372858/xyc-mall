package org.xyc.app.trade.service;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.xyc.app.basic.model.SecurityUser;
import org.xyc.domain.base.util.Assert;
import org.xyc.domain.order.common.constant.OrderStatusConstant;
import org.xyc.domain.order.facade.OrderReadFacade;
import org.xyc.domain.order.facade.OrderWriteFacade;
import org.xyc.domain.order.model.request.OrderCreateRequest;
import org.xyc.domain.order.model.to.OrderTO;

/**
 * @author xuyachang
 * @date 2024/4/14
 */
@Service
@RequiredArgsConstructor
public class TradeService {

    private final OrderReadFacade orderReadFacade;

    private final OrderWriteFacade orderWriteFacade;

    public String createOrder(OrderCreateRequest orderCreateRequest) {
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityUser user = (SecurityUser)context.getAuthentication().getPrincipal();
        orderCreateRequest.setBuyerMobile(user.getUserTO().getPhone());
        orderCreateRequest.setBuyerName(user.getUserTO().getName());
        orderCreateRequest.setShopId(orderCreateRequest.getShopId());
        OrderTO newOrder = Assert.getResult(orderWriteFacade.createOrder(orderCreateRequest));
        OrderTO order = Assert.getResult(orderReadFacade.findOrder(newOrder));
        return JSON.toJSONString(order);
    }

    public String orderPaid(OrderTO orderTO) {
        orderTO.setOrderStatus(OrderStatusConstant.PAID);
        Boolean result = Assert.getResult(orderWriteFacade.updateOrderStatus(orderTO));
        return result.toString();
    }

    public String orderNone(OrderTO orderTO) {
        orderTO.setOrderStatus(OrderStatusConstant.DONE);
        Boolean result = Assert.getResult(orderWriteFacade.updateOrderStatus(orderTO));
        return result.toString();
    }
}
