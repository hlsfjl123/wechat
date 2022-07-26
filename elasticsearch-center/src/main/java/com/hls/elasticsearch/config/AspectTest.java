package com.hls.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: User-XH251
 * @Date: 2022/7/21 10:09
 */

@Slf4j
@Aspect
@Component
@Order(1)//执行顺序
public class AspectTest {

    @Pointcut("execution(public * com.hls.elasticsearch.controller.*.*(..))")
    public void methodPointCut() {
    }

    @Around("methodPointCut()")
    public void around(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        log.info("请求的方法为：{}", method.getName());
    }

    @Before("methodPointCut()")
    public void before(JoinPoint joinPoint) {
        getParams(joinPoint);
        log.info("方法之前执行");
    }

    @After("methodPointCut()")
    public void after(JoinPoint joinPoint) {
        getParams(joinPoint);
        log.info("方法之后执行");
    }

    private static void getParams(JoinPoint joinPoint) {
        if (joinPoint == null) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                System.out.println(arg);
            }
        }

    }
}
