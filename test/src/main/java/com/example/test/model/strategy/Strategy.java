package com.example.test.model.strategy;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 10:49
 * 策略模式
 */
public interface Strategy {

    /**
     * 获取支付方式
     *
     * @return 响应，支付方式
     */
    String getPayType();
    /**
     * 支付调用
     *
     * @param order 订单信息
     * @return 响应，支付结果
     */
    String pay(String order);
}
