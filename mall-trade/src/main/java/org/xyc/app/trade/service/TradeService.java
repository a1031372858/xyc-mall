package org.xyc.app.trade.service;

import com.alibaba.fastjson2.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.xyc.app.basic.constant.UrlConstant;
import org.xyc.app.basic.model.SecurityUser;
import org.xyc.app.basic.util.OKHttpUtil;
import org.xyc.domain.order.model.request.OrderCreateRequest;

/**
 * @author xuyachang
 * @date 2024/4/14
 */
@Service
public class TradeService {

    public String createOrder(OrderCreateRequest orderCreateRequest) {
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityUser user = (SecurityUser)context.getAuthentication().getPrincipal();
        String path = UrlConstant.ORDER_URL+UrlConstant.CREATE_ORDER;
        orderCreateRequest.setBuyerMobile(user.getUserTO().getPhone());
        orderCreateRequest.setBuyerName(user.getUserTO().getName());
        String result = OKHttpUtil.postJson(path, orderCreateRequest);
        return result;
    }
}
