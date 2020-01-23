package com.yb.cheung.modules.bss.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BssMethodLogService {

    //开启环绕方法
    //根据方法名包含update/insert/delete进行日志记录
    @Pointcut("@annotation(com.yb.cheung.modules.bss.annotation.BssMethodLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        
        return point.proceed();
    }
}
