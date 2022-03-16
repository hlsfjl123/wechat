package com.hls.alibaba.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: User-XH251
 * @Date: 2022/3/16 13:53
 */
@Data
@Component
@ConfigurationProperties("jwt")
public class JwtConfig {

    private String secret;

    private String privateKey;

    private String publicKey;
}
