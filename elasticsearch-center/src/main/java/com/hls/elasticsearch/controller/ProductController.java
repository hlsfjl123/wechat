package com.hls.elasticsearch.controller;


import com.hls.elasticsearch.entity.Product;
import com.hls.elasticsearch.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: User-XH251
 * @Date: 2022/7/18 17:41
 */
@RestController
@RequestMapping(value = "product")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "search")
    public List<Product> search() {
        try {
            return productService.search2();
        } catch (Exception e) {
            log.error("es查询失败",e);
            return null;
        }
    }


}
