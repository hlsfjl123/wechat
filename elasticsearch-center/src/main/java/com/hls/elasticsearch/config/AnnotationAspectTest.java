package com.hls.elasticsearch.config;

import com.hls.elasticsearch.annotation.TestAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: User-XH251
 * @Date: 2022/7/21 15:31
 */
@Slf4j
@Aspect
@Component
@Order(2)//执行顺序
public class AnnotationAspectTest {

    @Pointcut("@annotation(com.hls.elasticsearch.annotation.TestAnnotation)")
    public void methodPointCut() {
    }

    @Around("methodPointCut()")
    public Object Around(ProceedingJoinPoint point) throws Throwable {
        System.out.println("AnnotationAspectTest Around start ");
        //获取注解和注解的值
        TestAnnotation annotation = getAnnotation(point);
        if (annotation != null) {
            boolean flag = annotation.flag();
            System.out.println("注解flags的值:" + flag);
        }
        //获取参数
        Object[] args = point.getArgs();

        for (Object arg : args) {
            System.out.println("arg ==>" + arg);
        }

        //去调用被拦截的方法
        Object proceed = point.proceed();

        return proceed;
    }

    //获取注解
    public TestAnnotation getAnnotation(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(TestAnnotation.class);
        }
        return null;
    }
}
