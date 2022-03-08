package com.hls.alibaba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hls.alibaba.entity.Product;
import com.hls.alibaba.httpstatus.ResultStatus;
import com.hls.alibaba.mapper.ProductMapper;
import com.hls.alibaba.service.ProductService;
import com.hls.alibaba.vo.ObjectRestResponse;
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
    public ObjectRestResponse<Product> getProductById(Integer id) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getId, id);
        Product product = baseMapper.selectOne(queryWrapper);
        if (product == null) {
            return new ObjectRestResponse<Product>().setResult(ResultStatus.NO_DATA);
        }
        return new ObjectRestResponse<Product>().setData(product);
    }
}
