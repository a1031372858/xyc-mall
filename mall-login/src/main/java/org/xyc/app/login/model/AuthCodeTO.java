package org.xyc.app.login.model;

import lombok.Data;

import java.util.Date;

/**
 * @author xuyachang
 * @date 2024/4/1
 */
@Data
public class AuthCodeTO {
    /**
     * 验证码
     */
    private String code;

    /**
     * 创建时间
     */
    private Date createAt;
}
