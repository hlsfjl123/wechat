package com.hls.alibaba.vo;

import com.hls.alibaba.entity.User;
import org.springframework.beans.factory.FactoryBean;

public class UserBeanFactory implements FactoryBean<User> {
    public User getObject() throws Exception {
        User user = new User();
        user.setUserName("你爹");
        return user;
    }

    public Class<?> getObjectType() {
        return User.class;
    }
}
