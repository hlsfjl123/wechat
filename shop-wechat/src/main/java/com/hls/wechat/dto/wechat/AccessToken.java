package com.hls.wechat.dto.wechat;

import lombok.Data;

/**
 * @Author: User-XH251
 * @Date: 2022/6/23 14:38
 */
@Data
public class AccessToken {
    /**
     * 接口访问凭证
     */
    private String accessToken;
    /**
     * 过期时间(提前5分钟刷新  5分钟内新老token都可用 保证平滑度过)
     */
    private Long expiresIn;

    public AccessToken(String accessToken, Long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn - (5 * 60);
    }

}
