package com.hls.wechat.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author : zhenguo.yao 2021-05-08 11:09
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate originRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }

    /**
     * clientHttpRequestFactory
     * @return
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager();
        //设置整个连接池最大连接数 根据自己的场景决定
        pollingConnectionManager.setMaxTotal(200);
        //路由是对maxTotal的细分
        pollingConnectionManager.setDefaultMaxPerRoute(100);
        RequestConfig requestConfig = RequestConfig.custom()
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(pollingConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .build();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        //服务器返回数据(response)的时间，超过该时间抛出read timeout
        clientHttpRequestFactory.setReadTimeout(10_000);
        //连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
        clientHttpRequestFactory.setConnectTimeout(5_000);
        //从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
        clientHttpRequestFactory.setConnectionRequestTimeout(5_000);
        return clientHttpRequestFactory;
    }

}
