package com.hls.alibaba.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 13:16
 */
@Data
@TableName(value = "shop_product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
