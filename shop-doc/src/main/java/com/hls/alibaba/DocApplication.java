package com.hls.alibaba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: User-XH251
 * @Date: 2022/3/30 10:57
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = "com.hls.alibaba.mapper")
public class DocApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocApplication.class,args);
    }
}
