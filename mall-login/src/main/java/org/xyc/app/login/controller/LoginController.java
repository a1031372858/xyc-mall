package org.xyc.app.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("byPhone")
    public String loginByPhone(@RequestBody UserTO userTO){
        return userDetailsService.loadUserByUsername(userTO.getPhone()).toString();
    }
}
