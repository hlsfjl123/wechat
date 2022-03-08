package com.hls.alibaba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 13:31
 */
@Data
@TableName(value = "shop_order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uId;

    private String uName;

    private Integer pId;

    private String pName;

    private BigDecimal pPrice;

    private Integer number;
}
