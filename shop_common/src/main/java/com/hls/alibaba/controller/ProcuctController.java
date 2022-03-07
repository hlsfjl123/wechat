package com.hls.alibaba.controller;

import com.hls.alibaba.entity.Product;
import com.hls.alibaba.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 13:22
 */
@RestController
@RequestMapping(value = "/product")
public class ProcuctController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "/add")
    public void addProduct(@RequestBody Product product) {
        productService.insert(product);
    }

}
