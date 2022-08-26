package com.hls.wechat;

import com.hls.wechat.peoperties.WeChatProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.hls.wechat.mapper")
@EnableConfigurationProperties(value = WeChatProperties.class)
public class ShopWechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopWechatApplication.class, args);
	}

}
