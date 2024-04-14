package org.xyc.app.trade.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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
