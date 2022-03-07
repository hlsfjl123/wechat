package com.hls.alibaba.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hls.alibaba.entity.Product;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 15:35
 */
public interface ProductService extends IService<Product> {

    public void insert(Product product);
}
