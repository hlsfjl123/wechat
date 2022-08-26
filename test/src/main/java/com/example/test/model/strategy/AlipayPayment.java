package com.example.test.model.strategy;

import org.springframework.stereotype.Component;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 10:53
 */
@Component
public class AlipayPayment implements Strategy {
    @Override
    public String getPayType() {
        return "alipay";
    }

    @Override
    public String pay(String order) {
        return "阿里支付成功";
    }
}
