package com.hls.wechat.enu;

import lombok.Getter;

/**
 * @Author: User-XH251
 * @Date: 2022/7/8 15:42
 */
@Getter
public enum AuthorizationTypeEnum {
    /**
     * 网页开发授权类型
     */
    SNSAPI_BASE(1, "snsapi_base"),
    SNSAPI_USERINFO(2, "snsapi_userinfo");

    private Integer value;
    private String desc;

    AuthorizationTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
