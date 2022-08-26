package com.example.test.model.strategy;

import org.springframework.stereotype.Component;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 10:55
 */
@Component
public class WxpayPayment implements Strategy {
    @Override
    public String getPayType() {
        return "wxpay";
    }

    @Override
    public String pay(String order) {
        return "微信支付成功";
    }
}
