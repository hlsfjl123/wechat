package com.example.test.model.strategy;

import org.springframework.stereotype.Component;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 10:54
 */
@Component
public class BankpayPayment implements Strategy {
    @Override
    public String getPayType() {
        return "bankpay";
    }

    @Override
    public String pay(String order) {
        return "银行支付成功";
    }
}
