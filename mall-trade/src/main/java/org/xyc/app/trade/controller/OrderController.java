package org.xyc.app.trade.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xyc.app.trade.service.TradeService;
import org.xyc.domain.order.model.request.OrderCreateRequest;

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
}
