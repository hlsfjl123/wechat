package com.hls.alibaba.aop.handler;

import com.hls.alibaba.aop.annotaion.ResponseResult;
import com.hls.alibaba.vo.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: User-XH251
 * @Date: 2022/3/11 16:16
 */
@Slf4j
@Component
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice {
    
    private static final String RESPONSE_RESULT_ANNOTAION = "RESPONSE_RESULT_ANNOTAION";
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        ServletRequestAttributes servletRequestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        ResponseResult result = (ResponseResult)request.getAttribute(RESPONSE_RESULT_ANNOTAION);
        return result==null?false:true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
      log.info("返回体重新包装中");
//       if(o instanceof ErrorResult){
//
//       }
        return new ObjectRestResponse<>().setData(o);
    }
}
