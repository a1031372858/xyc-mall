package org.xyc.app.login.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.xyc.app.login.model.AuthCodeTO;
import org.xyc.app.login.service.LoginService;
import org.xyc.app.login.token.SmsAuthenticationToken;
import org.xyc.domain.base.exception.BusinessException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuyachang
 * @date 2024/3/27
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login",
            "POST");

    public SmsAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public SmsAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER,authenticationManager);
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LoginService loginService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String authCode = request.getParameter("password");
        AuthCodeTO authCodeTO = loginService.getUserAuthCodeTO(username);
        if(authCode == null){
            throw new BusinessException("验证码为空");
        }
        if(authCodeTO == null || authCodeTO.getCode() == null){
            throw new BusinessException("请先获取验证码");
        }
        if(!authCode.equals(authCodeTO.getCode())){
            throw new BusinessException("验证码错误");
        }
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if(user == null){
            throw new BusinessException("用户不存在");
        }
        loginService.removeUserAuthCodeTO(username); 
        return new SmsAuthenticationToken(user,null,user.getAuthorities());
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
