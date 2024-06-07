package org.xyc.app.basic.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xyc.domain.base.model.Response;

/**
 * 全局异常处理
 * @author xuyachang
 * @date 2024/6/2
 */
@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response<Object> bindExceptionHandler(Exception e) {
        return Response.fail(e.getMessage());
    }
}
