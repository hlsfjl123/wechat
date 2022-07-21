package com.hls.alibaba.predicates.filters;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.hls.alibaba.config.JwtConfig;
import com.hls.alibaba.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
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

    @Autowired
    private JwtConfig jwtConfig;

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
        /**
         * /从token中解析用户信息并设置到Header中去
         */
        try {
            Jws<Claims> claimsJws = JwtUtil.parserToken(token, jwtConfig.getSecret());
            ServerHttpRequest request = exchange.getRequest().mutate().header("username", claimsJws.getBody().getIssuer()).build();
            exchange = exchange.mutate().request(request).build();
        } catch (Exception e) {
            e.printStackTrace();
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
