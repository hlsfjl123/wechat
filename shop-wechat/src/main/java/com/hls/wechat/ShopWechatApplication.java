package com.hls.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.hls.wechat.mapper")
public class ShopWechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopWechatApplication.class, args);
	}

}
