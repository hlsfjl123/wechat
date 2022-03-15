package com.hls.alibaba.predicates.filters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (!StringUtils.equals("admin", token)) {
            log.info("认证失败");
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.valueOf(HttpStatus.SC_UNAUTHORIZED));
            return  exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    //表示当前过滤器的优先级  越小级别越高
    @Override
    public int getOrder() {
        return 0;
    }
}
