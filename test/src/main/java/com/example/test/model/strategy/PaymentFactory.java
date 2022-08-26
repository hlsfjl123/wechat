package com.example.test.model.strategy;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 11:01
 */
public class PaymentFactory {
    private static final Map<String, Strategy> payStrategies = new HashMap<>();

    static {
        payStrategies.put("alipay", new AlipayPayment());
        payStrategies.put("bankpay",new BankpayPayment());
        payStrategies.put("wxpay",new WxpayPayment());
    }

    public static Strategy getPaymeny(String paymentType) {
        if (!StringUtils.hasText(paymentType) || !payStrategies.containsKey(paymentType)) {
            System.out.println("支付方式不存在");
        }
        return payStrategies.get(paymentType);
    }
}
