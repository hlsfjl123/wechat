package com.hls.alibaba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 13:34
 */

@Data
@TableName(value = "shop_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String password;

    private String telPhone;

}
