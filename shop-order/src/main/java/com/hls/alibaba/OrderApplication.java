package com.hls.alibaba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: User-XH251
 * @Date: 2022/3/8 9:54
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.hls.alibaba.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
