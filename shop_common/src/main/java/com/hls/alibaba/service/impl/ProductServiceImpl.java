package com.hls.alibaba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hls.alibaba.entity.Product;
import com.hls.alibaba.mapper.ProductMapper;
import com.hls.alibaba.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 15:36
 */
@Slf4j
@Service
public class ProductServiceImpl  extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Override
    public void insert(Product product) {
        baseMapper.insert(product);
    }
}
