package com.hls.alibaba.predicates.filters;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Component
@ConfigurationProperties("my.jwt")
@Data
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private String[] skipAuthUrls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path != null && Arrays.asList(skipAuthUrls).contains(path)) {
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        if(StringUtils.isBlank(token)){
            response.setStatusCode(HttpStatus.OK);
            byte[] returnResponse = "{\"code\": \"401\",\"msg\": \"401 Unauthorized.\"}".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(returnResponse);
            return response.writeWith(Flux.just(buffer));
        }

        return chain.filter(exchange);
    }

    /**
     * 表示当前过滤器的优先级  越小级别越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
