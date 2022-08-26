package com.example.test.model.test;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 17:45
 */
public class Optionals {
    public static void main(String[] args) {
        List<User> objects2 = new ArrayList<>();
        Supplier<User> runnable = User::new;
        User user = runnable.get();
        boolean present = Optional.of(user).isPresent();
        System.out.println(present);
       //User user=null;
        User houlinshuai = Optional.ofNullable(user).orElse(new User("西门狗蛋", "13122202638"));
        System.out.println(houlinshuai);

        objects2.add(houlinshuai);
        User user5=new User();
        user5.setUsername("houlinshuai");
        Optional.ofNullable(user5).ifPresent(s-> objects2.add(s));
        System.out.println(JSON.toJSONString(objects2));
    }
}
