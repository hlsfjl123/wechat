package com.hls.alibaba.aop.annotaion;

import java.lang.annotation.*;

/**
 * @Author: User-XH251
 * @Date: 2022/3/18 16:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
public @interface VerifyToken {
    /**
     * 是否启用token校验，默认启用
     */
    boolean enable() default true;
}
