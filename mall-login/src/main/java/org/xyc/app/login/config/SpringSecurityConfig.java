package org.xyc.app.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * @author xuyachang
 * @date 2024/3/21
 */
@Configuration
public class SpringSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyMD5PasswordEncoder();
    }
}
