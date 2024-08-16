package org.xyc.app.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.xyc.app.basic.model.SecurityUser;
import org.xyc.app.basic.util.UserInfoHolder;
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

    private final UserDetailsService userDetailsService;

    @Autowired
    private LoginService loginService;

    @PostMapping("byPhone")
    public String loginByPhone(@RequestBody UserTO userTO){
        return userDetailsService.loadUserByUsername(userTO.getPhone()).toString();
    }

    /**
     * 生成登录验证码
     * @param userTO
     * @return
     */
    @PostMapping("makeAuthCode")
    public String makeAuthCode(@RequestBody UserTO userTO){
        return loginService.makeUserAuthCode(userTO.getPhone());
    }

    /**
     * 登录测试接口
     * @return
     */
    @GetMapping("hello")
    public String hello(){
       UserTO userTO = UserInfoHolder.getUserTO();
        return "Hello " + userTO.getName();
    }
}
