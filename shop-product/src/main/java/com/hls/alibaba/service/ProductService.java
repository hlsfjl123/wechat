package com.hls.alibaba.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hls.alibaba.entity.Product;
import com.hls.alibaba.vo.ObjectRestResponse;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @Author: User-XH251
 * @Date: 2022/3/8 9:53
 */
public interface ProductService extends IService<Product> {

    public Product getProductById(Integer id);

    public boolean insertProduct(Product product);

    public void export(HttpServletResponse response);

    public void getExcelTemplate(HttpServletResponse response);

    public void importProduct(InputStream inputStream);
}
