package com.hls.alibaba.aop;

import com.hls.alibaba.aop.annotaion.VerifyToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * aop+注解校验token(适用于单一服务)
 * @Author: User-XH251
 * @Date: 2022/3/18 16:34
 */
@Slf4j
@Aspect
//@Component
public class CheckTokenAspect {

    @Pointcut("execution(public * com.hls.alibaba.*.*(..))")
    public void methodPointCut() {
    }
    @Before("methodPointCut()")
    public Object before(JoinPoint joinPoint) {
        boolean enable = verifyTokenEnable(joinPoint);
        if(!enable){
            return joinPoint;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestHeader = request.getHeader("Authorization");
        if(StringUtils.isEmpty(requestHeader)){

        }
        //后续redis token获取
        //token校验
        //token续签
        return joinPoint;
    }

    public boolean verifyTokenEnable(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        VerifyToken annotation = method.getAnnotation(VerifyToken.class);
        if(annotation==null){
           return  true;
        }
        return annotation.enable();
    }
}
