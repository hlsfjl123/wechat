package com.hls.alibaba.aop.annotaion;

import java.lang.annotation.*;

/**
 * @Author: User-XH251
 * @Date: 2022/3/11 15:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface ResponseResult {
}
