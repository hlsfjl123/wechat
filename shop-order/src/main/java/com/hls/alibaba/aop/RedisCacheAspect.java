package com.hls.alibaba.aop;

import com.hls.alibaba.aop.annotaion.RedisCache;
import com.hls.alibaba.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Cacheable springboot提供简单的缓存注解
 * @Author: User-XH251
 * @Date: 2022/3/28 10:43
 */
@Aspect
@Component
@Slf4j
public class RedisCacheAspect {

    private static final String CATCHE_PREFIX = "hls::cache";

    @Autowired
    RedisUtil redisUtil;

    @Pointcut("execution(public * com.hls.alibaba.controller.*.*(..))")
    public void cachePointCut() {
    }

    @Around("cachePointCut()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        String key = CATCHE_PREFIX +"::"+ methodName;
        RedisCache annotation = method.getAnnotation(RedisCache.class);
        boolean enable = annotation.enable();
        if (!enable) {
            return joinPoint.proceed();
        }
        boolean flush = annotation.flush();
        if (flush) {
            redisUtil.deleteByKeys(key);
            return joinPoint.proceed();
        }
        boolean hasKey = redisUtil.hasKey(key);
        if (hasKey) {
            log.info("获取到得值是{}",redisUtil.get(key));
            return redisUtil.get(key);
        }
        Object result = joinPoint.proceed();
        log.info("运行结果为{}",result);
        redisUtil.set(key, result, annotation.expire(), annotation.unit());
        return result;
    }
}
