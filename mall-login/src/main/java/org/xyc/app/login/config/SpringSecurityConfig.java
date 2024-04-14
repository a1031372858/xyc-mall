package org.xyc.app.login.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.xyc.app.login.filter.SmsAuthenticationFilter;

/**
 * @author xuyachang
 * @date 2024/3/21
 */
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SpringSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyMD5PasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(smsAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class);

        return http.authorizeRequests()
                .antMatchers("/mall/login/makeAuthCode")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/mall/login/hello")
                .and()
                .rememberMe()
                .and()
                .csrf().disable()
                .build();
    }


    @Bean
    public SmsAuthenticationFilter smsAuthenticationFilter(HttpSecurity http) throws Exception {
        SmsAuthenticationFilter filter = new SmsAuthenticationFilter();
        AuthenticationManager manager = http.getSharedObject(AuthenticationManagerBuilder.class).build();
        filter.setAuthenticationManager(manager);
        return filter;
    }

}
