package org.xyc.app.basic.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.xyc.app.basic.model.SecurityUser;
import org.xyc.domain.user.model.to.UserTO;

/**
 * @author xuyachang
 * @date 2024/6/23
 */
public class UserInfoHolder {

    /**
     * 获取登录用户信息
     * @return
     */
    public static UserTO getUserTO(){
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityUser user = (SecurityUser)context.getAuthentication().getPrincipal();
        return user.getUserTO();
    }
}
