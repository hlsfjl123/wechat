package com.hls.alibaba.service;

import com.hls.alibaba.vo.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product")
public interface ProductService {
    @GetMapping(value = "/product/{id}")
    ObjectRestResponse getProductById(@PathVariable(value = "id") Integer id);
}
