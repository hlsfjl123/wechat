package com.hls.alibaba.vo;

import com.hls.alibaba.httpstatus.ResultStatus;

/**
 * @Author: User-XH251
 * @Date: 2022/3/8 10:40
 */
public class ObjectRestResponse<T> extends BaseResponse {

    private T data;

    public T getData() {
        return data;
    }

    public ObjectRestResponse<T> setData(T data) {
        this.data=data;
        return this;
    }

    @Override
    public ObjectRestResponse<T> setResult(ResultStatus result){
        super.setResult(result);
        return this;
    }

    @Override
    public ObjectRestResponse<T> setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public ObjectRestResponse<T> setMessage(String message) {
        super.setMessage(message);
        return this;
    }
}
