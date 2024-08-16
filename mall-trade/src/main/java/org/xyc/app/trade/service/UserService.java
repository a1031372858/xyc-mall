package org.xyc.app.trade.service;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xyc.app.basic.util.UserInfoHolder;
import org.xyc.domain.base.model.Response;
import org.xyc.domain.base.util.Assert;
import org.xyc.domain.order.facade.OrderWriteFacade;
import org.xyc.domain.order.model.to.OrderTO;
import org.xyc.domain.user.facade.UserWriteFacade;
import org.xyc.domain.user.model.to.UserTO;

/**
 * @author xuyachang
 * @date 2024/6/23
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserWriteFacade userWriteFacade;

    private final OrderWriteFacade orderWriteFacade;

    @GlobalTransactional
    public String updateUserName(UserTO userTO) {
        UserTO loginUser = UserInfoHolder.getUserTO();
        userTO.setId(loginUser.getId());
        Assert.getResult(userWriteFacade.updateUserName(userTO));
        OrderTO orderTO = new OrderTO();
        orderTO.setBuyerId(userTO.getId());
        orderTO.setBuyerName(userTO.getName());
        Assert.getResult(orderWriteFacade.updateOrderBuyerInfo(orderTO));
        //更新内存中的用户名
        loginUser.setName(userTO.getName());
        return "更新成功";
    }
}
