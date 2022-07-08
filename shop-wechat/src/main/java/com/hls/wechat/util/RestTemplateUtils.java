package com.hls.wechat.util;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: linshuaihou
 * @Date: 2022/5/16 14:46
 */
@Component
@Slf4j
public class RestTemplateUtils {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 请求头参数
     *
     * @return
     */
    public static HttpHeaders restHead() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


    /**
     * get请求 map形式参数
     *
     * @param url
     * @return
     */
    public String getByMap(String url) {
        //设置请求头
        HttpEntity httpEntity = new HttpEntity<>(restHead());
        ResponseEntity<String> restResp = exchange(url, HttpMethod.GET, httpEntity);
        if (!restResp.getStatusCode().equals(HttpStatus.OK)) {
            log.error("调用接口:{}失败,请求状态:{},请求参数:{}", url, restResp.getStatusCode(), null);
        }
        String respData = restResp.getBody();
        return respData;
    }

    /**
     * post 请求
     *
     * @param url
     * @param req
     * @return
     */
    public String postByMap(String url, Object req) {
        //设置请求头
        HttpEntity httpEntity = new HttpEntity(JSONUtil.toJsonStr(req), restHead());
        ResponseEntity<String> restResp = exchange(url, HttpMethod.POST, httpEntity);
        if (!restResp.getStatusCode().equals(HttpStatus.OK)) {
            log.error("调用API接口:{}失败,请求状态:{},请求参数:{}", url, restResp.getStatusCode(), req);
        }
        String respData = restResp.getBody();
        log.info("调用信息:{}", respData);
        return respData;
    }

    public ResponseEntity<String> exchange(String url, HttpMethod httpMethod, HttpEntity httpEntity) {
        printInfo(url, httpEntity.getBody());
        ResponseEntity<String> restResp = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
        return restResp;
    }

    public void printInfo(String url, Object object) {
        log.info("调用外部API接口:{} ,参数:{} ", url, object);
    }
}
