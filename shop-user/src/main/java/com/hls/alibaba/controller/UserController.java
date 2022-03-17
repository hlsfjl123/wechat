package com.hls.alibaba.controller;

import com.hls.alibaba.dto.UserResponse;
import com.hls.alibaba.entity.User;
import com.hls.alibaba.service.UserService;
import com.hls.alibaba.utils.JwtUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 13:22
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/add")
    public void addProduct(@RequestBody User user) {
        userService.insert(user);
    }

    @PostMapping(value = "/login")
    public UserResponse login(@RequestBody User user) {
        UserResponse userResponse = new UserResponse();
        User returnUser = userService.selectUserByTelAndPassword(user);
        if (returnUser != null) {
            JwtUtils jwtUtils = new JwtUtils();
            String token = jwtUtils.createToken(user);
            userResponse.setToken(token);
        }
        return userResponse;
    }

}
