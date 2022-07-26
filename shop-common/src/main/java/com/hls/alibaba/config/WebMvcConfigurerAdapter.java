package com.hls.alibaba.config;

import com.hls.alibaba.interceptor.ResponseResultInterceptor;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: User-XH251
 * @Date: 2022/3/11 17:06
 */
@Configuration
public class WebMvcConfigurerAdapter implements WebMvcConfigurer {
    /**
     * 如果不注入  拦截器类似@Value无法注入值
     * @return
     */
    @Bean
    public ResponseResultInterceptor init(){
        return new ResponseResultInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(init()).addPathPatterns("/**");
    }

}
