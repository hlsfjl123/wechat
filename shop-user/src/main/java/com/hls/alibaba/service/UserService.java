package com.hls.alibaba.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hls.alibaba.entity.Product;
import com.hls.alibaba.entity.User;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 15:35
 */
public interface UserService extends IService<User> {

    public void insert(User user);
}
