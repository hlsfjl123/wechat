package com.hls.wechat.peoperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: User-XH251
 * @Date: 2022/7/8 13:32
 */
@ConfigurationProperties(prefix = "wechat")
@Component
@Data
public class WeChatProperties {

    private String appId;

    private String appSecret;

    private String accessTokenUrl;

    private String unionIdUrl;

    private String ticketUrl;

    private String qrCodeUrl;

    private String authUrl;

    private String authAccessTokenUrl;

    private String authRefreshTokenUrl;

    private String userInfoUrl;
}
