package com.hls.alibaba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: User-XH251
 * @Date: 2022/3/8 9:54
 */
@SpringBootApplication
@MapperScan(basePackages = "com.hls.alibaba.mapper")
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }
}
