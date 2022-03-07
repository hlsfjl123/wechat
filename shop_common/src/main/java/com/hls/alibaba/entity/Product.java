package com.hls.alibaba.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 13:16
 */
@Data
@TableName(value = "shop_product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Integer id;

    private String name;

    private Double price;
    /**
     * 库存
     */
    private Integer stock;
}
