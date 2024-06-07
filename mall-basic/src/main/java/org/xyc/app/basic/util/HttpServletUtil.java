package org.xyc.app.basic.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author xuyachang
 * @date 2024/1/13
 */
public class HttpServletUtil {


    /**
     * 获取 Session 并指定超时时间
     * @return HttpSession
     */
    public static HttpSession getSession(){
        return getRequest(true).getSession();
    }

    public static HttpServletRequest getRequest(Boolean requireNonNull) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            if (requireNonNull) {
                throw new RuntimeException("null");
            } else {
                return null;
            }
        } else {
            return requestAttributes.getRequest();
        }
    }

    public static HttpServletRequest getRequest() {
        return getRequest(false);
    }

}
