package org.xyc.app.trade.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xyc.app.trade.service.UserService;
import org.xyc.domain.user.model.to.UserTO;

/**
 * @author xuyachang
 * @date 2024/6/23
 */
@RequestMapping("mall/user")
@RequiredArgsConstructor
@RestController
public class UserInfoController {

    private final UserService userService;

    @GetMapping("/updateUserName")
    public String updateUserName(UserTO userTO){
        return userService.updateUserName(userTO);
    }
}
