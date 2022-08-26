package com.example.test.model.lamada;

import org.apache.logging.log4j.util.Strings;

import java.util.function.Function;

/**
 * 函数式接口
 * @Author: User-XH251
 * @Date: 2022/8/26 16:52
 */
public class Functions {
    public static void main(String[] args) {

       // Function<String, Integer> function = Integer::valueOf;
       // Function<String, String> stringFunction = function.andThen(String::valueOf);
        Function<String, String> booleanFunction = new Something()::startwhith;
        String apply = booleanFunction.apply("123");
        System.out.println(apply);

        Function<Integer, Integer> s1 =i->i*i;
        Function<Integer, Integer> s2 =i->i*5;
        Function<Integer, Integer> compose = s1.compose(s2);
        Integer integer = compose.apply(6);
        //900    6*5 平方
        System.out.println(integer);

        Function<Integer, Integer> function = s1.andThen(s2);
        Integer integer2 = function.apply(6);
        //180   6*6*5
        System.out.println(integer2);

        Integer apply1 = s1.apply(6);
        System.out.println(apply1);

        Function<String, Boolean> isBlank = Strings::isBlank;
        Boolean apply2 = isBlank.apply("");
        System.out.println(apply2);
    }
}
