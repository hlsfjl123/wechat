package com.example.test.model.strategy;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 16:04
 */
public class test {
    public static void main(String[] args) {
        Strategy alipay = PaymentFactory.getPaymeny("alipay");
        System.out.println(alipay.pay(""));
    }
}
