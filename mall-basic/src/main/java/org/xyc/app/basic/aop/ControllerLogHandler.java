package org.xyc.app.basic.aop;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.xyc.app.basic.util.HttpServletUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xuyachang
 * @date 2024/1/9
 */
@Slf4j
@Aspect
@Component
public class ControllerLogHandler {

    @Pointcut("execution(public * org.xyc..*.controller.*.*(..))")
    public void logPointCut(){};

    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint point) throws Throwable{
        HttpServletRequest request = HttpServletUtil.getRequest();
        if (Objects.isNull(request)) {
            return point.proceed();
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        MethodSignature signature = (MethodSignature) point.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = point.getArgs();
        Map<String, Object> argMap = new HashMap<>(args.length);
        for (int i = 0; i < parameterNames.length; i++) {
            String name = parameterNames[i];
            Object arg = args[i];
            argMap.put(name,arg);
        }
        log.info("----url={},args={}",request.getRequestURL(), JSON.toJSONString(argMap));
        Object result = point.proceed();
        stopWatch.stop();
        double totalTime = stopWatch.getTotalTimeMillis();
        log.info("----url={},time={}ms,response={}",request.getRequestURL(),totalTime, JSON.toJSONString(result));
        return result;
    }
}
