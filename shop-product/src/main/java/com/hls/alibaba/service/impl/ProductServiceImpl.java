package com.hls.alibaba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hls.alibaba.entity.Product;
import com.hls.alibaba.mapper.ProductMapper;
import com.hls.alibaba.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: User-XH251
 * @Date: 2022/3/8 9:54
 */
@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Override
    public Product getProductById(Integer id) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getId, id);
        Product product = baseMapper.selectOne(queryWrapper);
        return product;
    }

    @Override
    public boolean insertProduct(Product product) {
        return baseMapper.insert(product) > 0 ? true : false;
    }
}
