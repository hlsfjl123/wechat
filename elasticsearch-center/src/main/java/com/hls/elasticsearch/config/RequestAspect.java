package com.hls.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: User-XH251
 * @Date: 2022/7/21 10:09
 */

@Slf4j
@Aspect
@Component
public class RequestAspect {

    @Pointcut("execution(public * com.hls.elasticsearch.controller.TestController.*(..))")
    public void methodPointCut(){
    }

    @Before("methodPointCut()")
    public void around(ProceedingJoinPoint pjp){
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        log.info("请求的方法为：{}",method.getName());
    }
}
