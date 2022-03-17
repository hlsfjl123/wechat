package com.hls.alibaba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hls.alibaba.entity.User;
import com.hls.alibaba.mapper.UserMapper;
import com.hls.alibaba.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 15:36
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public void insert(User user) {
        baseMapper.insert(user);
    }

    @Override
    public User selectUserByTelAndPassword(User user) {
        User returnUser=null;
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getTelPhone,user.getTelPhone())
                               .eq(User::getPassword,user.getPassword());
        returnUser = baseMapper.selectOne(userLambdaQueryWrapper);
        return returnUser;
    }
}
