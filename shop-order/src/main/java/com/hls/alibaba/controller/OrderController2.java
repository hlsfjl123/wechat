package com.hls.alibaba.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.hls.alibaba.entity.Order;
import com.hls.alibaba.entity.Product;
import com.hls.alibaba.service.OrderService;
import com.hls.alibaba.service.ProductService;
import com.hls.alibaba.vo.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: User-XH251
 * @Date: 2022/3/9 9:14
 */
@Slf4j
@RestController
@RequestMapping(value = "/order2")
public class OrderController2 {
    @Value("${product-url}")
    String productUrl;
    @Autowired
    OrderService orderService;
//    @Autowired
//    RestTemplate restTemplate;
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    ProductService productService;

    @PostMapping(value = "/{id}")
    public ObjectRestResponse<Order> order(@PathVariable(value = "id") Integer id) {
        log.info("接收到商品号为{}的商品，接下来下订单", id);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObjectRestResponse restResponse = productService.getProductById(id);
        log.info("查询到的商品信息为{}", JSON.toJSONString(restResponse.getData()));
        Product product = JSONUtil.toBean(JSONUtil.toJsonStr(restResponse.getData()), Product.class);
        return null;
    }

    private Order assOrder(Product product){
        Order order = new Order();
        order.setUId(1);
        order.setUName("测试");
        order.setPId(product.getId());
        order.setPName(product.getName());
        order.setPPrice(product.getPrice());
        order.setNumber(1);
        return order;
    }
}
