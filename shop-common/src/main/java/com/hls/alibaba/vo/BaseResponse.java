package com.hls.alibaba.vo;

import com.hls.alibaba.httpstatus.ResultStatus;

import java.io.Serializable;

/**
 * @Author: User-XH251
 * @Date: 2022/3/8 10:41
 */
public class BaseResponse implements Serializable {

    private int status = 200;
    private String message = "success";

    public BaseResponse setResult(ResultStatus result) {
        this.status = result.getCode();
        this.message = result.getMsg();
        return this;
    }

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse() {}

    public String getMessage() {
        return message;
    }

    public BaseResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public BaseResponse setStatus(int status) {
        this.status = status;
        return this;
    }
}
