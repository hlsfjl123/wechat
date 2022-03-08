package com.hls.alibaba.httpstatus;

import lombok.*;

/**
 * @Author: User-XH251
 * @Date: 2022/3/8 10:56
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum  ResultStatus {
    /**
     * 未知错误
     */
    UNKNOW_ERROR(-1, "未知错误"),

    SUCCESS(200, "成功"),

    ERROR(100, "失败"),

    ADD_ERROR(101, "添加失败"),

    UPDATE_ERROR(102, "更新失败"),

    NO_DATA(204, "没有数据");

    private Integer code;

    private String msg;


}
