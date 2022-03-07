package com.hls.alibaba.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 13:31
 */
@Data
@TableName(value = "shop_order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer id;

    private Integer uid;

    private String username;
}
