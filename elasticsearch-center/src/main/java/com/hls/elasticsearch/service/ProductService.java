package com.hls.elasticsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hls.elasticsearch.entity.Product;


import java.io.IOException;
import java.util.List;

/**
 * @Author: User-XH251
 * @Date: 2022/7/18 17:21
 */
public interface ProductService extends IService<Product> {

    List<Product> search() throws IOException;

    List<Product> search2();
}
