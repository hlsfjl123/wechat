package com.hls.wechat.lamada;

import com.alibaba.fastjson.JSON;
import com.hls.wechat.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 10:33
 */
public class Test {
    public static void main(String[] args) {
        IUserFactory<User> factory = User::new;
        User user = factory.create("侯林帅", "13122202638");
        System.out.println(JSON.toJSONString(user));


        List<Integer> objects = new ArrayList<>();
        objects.add(1);
        objects.add(2);
        objects.add(3);
        objects.add(null);
        //Integer integer = objects.stream().filter(item -> item.equals(5)).findFirst().orElse(null);
        List<Integer> collect = objects.stream().filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println("collect"+JSON.toJSONString(collect));
//        System.out.println(integer);
//        String string="1";
//        UnblockedCallback isEmpty = string::isEmpty;
//        System.out.println(isEmpty);


        List<String> objects1 = new ArrayList<>();
        objects1.add("1");
        objects1.add("2");
        objects1.add("3");
        objects1.add(null);
        //Integer integer = objects.stream().filter(item -> item.equals(5)).findFirst().orElse(null);
        List<String> collect1 = objects1.stream().filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println("collect"+JSON.toJSONString(collect1));

        Predicate<Boolean> nonNull = Objects::nonNull;
        System.out.println("222222"+nonNull.negate());
        Function<String, Integer> stringIntegerBiIntFunction = Integer::valueOf;
        Function<String, String> stringFunction = stringIntegerBiIntFunction.andThen(String::valueOf);

        Supplier<User> callback = User::new;
        User user1 = callback.get();
        System.out.println(user1);

        Function<Integer, Integer> s1 =i -> i * i;
        Function<Integer, Integer> s2 =i -> i * 5;
        Function<Integer, Integer> compose = s1.compose(s2);
        Integer apply = compose.apply(5);
        System.out.println(apply);

        Function<String, String> identity = Function.identity();
        String apply1 = identity.apply("1");
        System.out.println(apply1);

        List<User> objects2 = new ArrayList<>();
        User user5=new User();
        user5.setUsername("houlinshuai");
        Optional.ofNullable(user5).ifPresent(s-> objects2.add(s));
        System.out.println(JSON.toJSONString(objects2));
    }
}
