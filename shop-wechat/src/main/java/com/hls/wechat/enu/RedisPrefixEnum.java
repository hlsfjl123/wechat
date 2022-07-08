package com.hls.wechat.enu;

import lombok.Getter;

/**
 * @Author: User-XH251
 * @Date: 2022/7/8 15:02
 */
@Getter
public enum RedisPrefixEnum {
    /**
     * redis accesstoken前缀
     */
    ACCESSTOKEN(1,"accesstoken:");

    private Integer value;
    private String desc;

    RedisPrefixEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
