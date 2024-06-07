package org.xyc.app.basic.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.springframework.stereotype.Component;
import org.xyc.domain.base.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sentinel流控处理器
 * @author xuyachang
 * @date 2024/6/2
 */
@Component
public class MallBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        if(e instanceof FlowException){
            throw new BusinessException("被限流了");
        }
    }
}
