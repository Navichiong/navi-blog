package com.lwc.naviblog.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        private RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

    @Pointcut(value = "execution(* com.lwc.naviblog.controller.*.*(..))")
    public void controllerLogPointCut() {

    }

    @Pointcut(value = "execution(* com.lwc.naviblog.controller.admin.*.*(..))")
    public void adminLogPointCut(){

    }

    @Before(value = "controllerLogPointCut()")
    public void logBefore(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request ==> {}" + requestLog);
    }

    @Before(value = "adminLogPointCut()")
    public void logBeforeAdmin(JoinPoint joinPoint){
        this.logBefore(joinPoint);
    }

    @AfterReturning(value = "adminLogPointCut()", returning = "result")
    public void logAfterAdmin(Object result){
        this.logAfterReturning(result);
    }

    @AfterReturning(value = "controllerLogPointCut()", returning = "result")
    public void logAfterReturning(Object result) {
        logger.info("Request Result ==> {} " + result);
    }
}

