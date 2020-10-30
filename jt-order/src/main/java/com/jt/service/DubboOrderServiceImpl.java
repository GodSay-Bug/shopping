package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author WL
 * @Date 2020-10-22 11:59
 * @Version 1.0
 */
@Service
public class DubboOrderServiceImpl implements DubboOrderService {

    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;


    @Override
    public String saveOrder(Order order) {

        // 订单id = 用户id+时间
        String orderId = "" + order.getUserId() + System.currentTimeMillis();

        // 完成订单入库
        order.setOrderId(orderId).setStatus(1);
        orderMapper.insert(order);

        // 订单物流入库
        OrderShipping orderShipping = order.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShippingMapper.insert(orderShipping);

        // 订单商品入库
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem item : orderItems) {
            order.setOrderId(orderId);
            orderItemMapper.insert(item);
        }
        System.out.println("订单入库成功");
        return orderId;
    }

    /**
     * 下单成功，回显订单信息
     *
     * @param id
     * @return
     */
    @Override
    public Order selectOrderByOrderId(String  id) {
        //  订单基本信息
        Order order = orderMapper.selectById(id);
        //  订单商品信息
        QueryWrapper<OrderItem> qw = new QueryWrapper<>();
        qw.eq("order_id", id);
        List<OrderItem> orderItems = orderItemMapper.selectList(qw);
        //  物流信息
        OrderShipping orderShipping = orderShippingMapper.selectById(id);

        return order.setOrderItems(orderItems).setOrderShipping(orderShipping);
    }
}
