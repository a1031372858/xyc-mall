package org.xyc.app.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xyc.app.basic.util.OKHttpUtil;
import org.xyc.app.login.service.LoginService;
import org.xyc.domain.user.model.to.UserTO;

/**
 * @author xuyachang
 * @date 2024/2/18
 */

@RequestMapping("mall/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("byPhone")
    public String loginByPhone(@RequestBody UserTO userTO){
        return loginService.loginByPhone(userTO.getPhone(),"123456");
    }
}
