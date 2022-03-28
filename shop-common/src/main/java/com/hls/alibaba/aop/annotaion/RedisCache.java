package com.hls.alibaba.aop.annotaion;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: User-XH251
 * @Date: 2022/3/28 10:29
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisCache {
    /**
     * 业务的名称
     *
     * @return
     */
    String value() default "";

    /**
     * redis缓存key值
     *
     * @return
     */
    String key() default "";

    /**
     * 是否刷新缓存，默认false
     */
    boolean flush() default false;

//    /**
//     * 过期策略
//     *
//     * @return ExpiredTime
//     */
//    ExpiredPolicy policy() default ExpiredPolicy.DEFAULT;

    /**
     * 缓存失效时间，默认3天
     */
    long expire() default 3L;

    /**
     * 缓存时间单位，默认天
     */
    TimeUnit unit() default TimeUnit.DAYS;

    /**
     * 是否启用缓存，默认启用
     */
    boolean enable() default true;

}
