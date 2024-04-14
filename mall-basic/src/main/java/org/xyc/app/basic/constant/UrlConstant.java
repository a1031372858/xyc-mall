package org.xyc.app.basic.constant;

/**
 * @author xuyachang
 * @date 2024/2/28
 */
public interface UrlConstant {

    String USER_URL = "http://localhost:8082";

    String ORDER_URL = "http://localhost:80";

    String USER_HOST = "localhost";

    String ORDER_HOST = "localhost";

    String FIND_USER_BY_PHONE = "/api/user/read/findByPhone";

    String CREATE_ORDER = "/api/order/write/create";

    int USER_PORT = 8082;

    int ORDER_PORT = 80;


}
