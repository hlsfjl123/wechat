package com.hls.wechat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 10:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;

    private String mobile;
}
