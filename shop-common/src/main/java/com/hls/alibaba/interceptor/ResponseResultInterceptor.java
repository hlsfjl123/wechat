package com.hls.alibaba.interceptor;

import com.hls.alibaba.aop.annotaion.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * @Author: User-XH251
 * @Date: 2022/3/11 16:00
 */
@Slf4j
@Component
public class ResponseResultInterceptor extends HandlerInterceptorAdapter {

    private static final String RESPONSE_RESULT_ANNOTAION = "RESPONSE_RESULT_ANNOTAION";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> clazz = handlerMethod.getClass();
            Method method = handlerMethod.getMethod();
            if (clazz.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(RESPONSE_RESULT_ANNOTAION, clazz.getAnnotation(ResponseResult.class));
            }
            if (method.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(RESPONSE_RESULT_ANNOTAION, method.getAnnotation(ResponseResult.class));
            }
        }
        return true;
    }

}
