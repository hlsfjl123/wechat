package com.hls.alibaba.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hls.alibaba.entity.Product;
import com.hls.alibaba.mapper.ProductMapper;
import com.hls.alibaba.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void export(HttpServletResponse response) {
        Page<Product> productPage = new Page<>(0, 20);
        Page<Product> selectPage = baseMapper.selectPage(productPage, null);
        List<Product> records = selectPage.getRecords();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("name", "产品名称");
        writer.addHeaderAlias("price", "价格");
        writer.addHeaderAlias("stock", "库存");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("updateTime", "修改时间");
        writer.write(records, true);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("product.xlsx", "UTF-8"));
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            writer.flush(outputStream, true);
            writer.close();
            IoUtil.close(outputStream);
        } catch (IOException e) {
            log.error("输出到响应流失败{}", e.getMessage());
        }

    }

    @Override
    public void getExcelTemplate(HttpServletResponse response) {
        ExcelReader reader = ExcelUtil.getReader(this.getClass().getResourceAsStream("/product.xlsx"));
        List<List<Object>> read = reader.read();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(read, true);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("product.xlsx", "UTF-8"));
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            writer.flush(outputStream, true);
            writer.close();
            IoUtil.close(outputStream);
        } catch (IOException e) {
            log.error("输出到响应流失败{}", e.getMessage());
        }
    }

    @Override
    public void importProduct(InputStream inputStream) {
        ExcelReader reader = ExcelUtil.getReader(inputStream, 0);
        List<List<Object>> read = reader.read(1, reader.getRowCount());
        List<Product> arrayList = new ArrayList<>();
        read.stream().forEach(item -> {
            Product product = new Product();
            Object id = item.get(0);
            Object name = item.get(1);
            Object price = item.get(2);
            Object stock = item.get(3);
            product.setId(Integer.valueOf(id.toString()));
            product.setName(name.toString());
            product.setPrice(BigDecimal.valueOf(Long.parseLong(price.toString())));
            product.setStock(Integer.valueOf(stock.toString()));
            arrayList.add(product);
            //baseMapper.insert(product);
        });
        this.saveBatch(arrayList);
    }
}
