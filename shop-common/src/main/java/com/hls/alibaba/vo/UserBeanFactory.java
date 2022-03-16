package com.hls.alibaba.vo;

import com.hls.alibaba.entity.User;
import org.springframework.beans.factory.FactoryBean;

public class UserBeanFactory implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        User user = new User();
        user.setUserName("你爹");
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
