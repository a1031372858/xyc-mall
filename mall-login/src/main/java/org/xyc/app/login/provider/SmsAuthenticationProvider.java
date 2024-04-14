package org.xyc.app.login.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuyachang
 * @date 2024/3/30
 */
@RequiredArgsConstructor
public class SmsAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserDetails user = userDetailsService.loadUserByUsername(req.getParameter("username"));
        if(user !=null){
            String code = "123456";
            String kaptcha = "123456";
            if(code.equals(kaptcha)){
                return super.createSuccessAuthentication(user,authentication,user);
            }
        }
        throw new AuthenticationServiceException("验证码输入错误");
    }
}
