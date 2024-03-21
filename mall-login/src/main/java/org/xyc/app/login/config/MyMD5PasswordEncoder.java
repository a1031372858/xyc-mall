package org.xyc.app.login.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author xuyachang
 * @date 2024/3/21
 */
public class MyMD5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }

}
