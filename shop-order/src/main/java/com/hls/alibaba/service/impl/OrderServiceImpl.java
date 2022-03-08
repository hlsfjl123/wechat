package com.hls.alibaba.service.impl;

import com.hls.alibaba.httpstatus.ResultStatus;
import com.hls.alibaba.mapper.OrderMapper;
import com.hls.alibaba.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hls.alibaba.entity.Order;
import com.hls.alibaba.vo.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: User-XH251
 * @Date: 2022/3/8 9:54
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Override
    public ObjectRestResponse<Order> insert(Order order) {
        return baseMapper.insert(order) > 0 ? new ObjectRestResponse<Order>().setResult(ResultStatus.SUCCESS) : new ObjectRestResponse<Order>().setResult(ResultStatus.ADD_ERROR);
    }
}
