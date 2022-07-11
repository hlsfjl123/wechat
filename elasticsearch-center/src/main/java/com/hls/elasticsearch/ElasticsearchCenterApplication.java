package com.hls.elasticsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.hls.elasticsearch.mapper")
public class ElasticsearchCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchCenterApplication.class, args);
	}

}
