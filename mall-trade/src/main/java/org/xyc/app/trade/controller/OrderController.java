package org.xyc.app.trade.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xyc.app.trade.service.TradeService;
import org.xyc.domain.order.common.constant.OrderStatusConstant;
import org.xyc.domain.order.model.request.OrderCreateRequest;
import org.xyc.domain.order.model.to.OrderTO;

/**
 * @author xuyachang
 * @date 2024/4/14
 */

@RequestMapping("mall/order")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final TradeService tradeService;

    @GetMapping("create")
    public String createOrder(OrderCreateRequest orderCreateRequest){
        return tradeService.createOrder(orderCreateRequest);
    }

    @GetMapping("paid")
    public String orderPaid(OrderTO orderTO){
        return tradeService.orderPaid(orderTO);
    }

    @GetMapping("none")
    public String orderNone(OrderTO orderTO){
        return tradeService.orderNone(orderTO);
    }
}
