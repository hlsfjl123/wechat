package com.hls.alibaba.controller;

import com.alibaba.fastjson.JSON;
import com.hls.alibaba.aop.annotaion.ResponseResult;
import com.hls.alibaba.entity.Product;
import com.hls.alibaba.service.ProductService;
import com.hls.alibaba.vo.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: User-XH251
 * @Date: 2022/3/8 9:51
 */
@Slf4j
@RestController

@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/{id}")
    @ResponseResult
    public Product getProductById(@PathVariable(value = "id") Integer id) {
        log.info("id为{}的商品查询", id);
        Product product = productService.getProductById(id);
        log.info("查询出来的商品为{}",product.toString());
        return product;
    }
    @PostMapping(value = "/insert")
    @ResponseResult
    public boolean insert(@RequestBody Product product){
      return   productService.insertProduct(product);
    }

}
