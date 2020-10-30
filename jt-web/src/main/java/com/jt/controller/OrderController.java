package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.thread.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author WL
 * @Date 2020-10-22 14:11
 * @Version 1.0
 */
@Controller
@RequestMapping("/order/")
public class OrderController {

    @Reference(check = false, timeout = 3000)
    private DubboOrderService dubboOrderService;

    @Reference(check = false, timeout = 3000)
    private DubboCartService dubboCartService;

    /**
     * http://www.jt.com/order/create.html
     * 订单结算页面
     *
     * @return
     */
    @RequestMapping("create")
    public String create(Model model) {
        // 根据userid查询订单信息
        User user = UserThreadLocal.get();
        Long userId = (long) user.getId();
        List<Cart> lists = dubboCartService.findCartListByUserId(userId);
        model.addAttribute("carts", lists);
        return "order-cart";
    }


    /**
     * 商品订单提交
     * http://www.jt.com/order/submit
     * 参数：表单的序列化提交 Order
     * return SysResult
     * 当订单存入后，要返回orderId，让用户查询
     */
    @ResponseBody
    @RequestMapping("submit")
    public SysResult saveOrder(Order order) {

        User user = UserThreadLocal.get();
        Long userId = (long) user.getId();
        order.setUserId(userId);
        // 保存订单信息，返回订单id
        String orderId = dubboOrderService.saveOrder(order);
        if (StringUtils.isEmpty(orderId))
            return SysResult.fail();
        else
            return SysResult.success(orderId);
    }

    /**
     * http://www.jt.com/order/success.html?id=null
     * @param id  订单id
     * ${order.orderId}
     */
    @RequestMapping("success")
    public String doSuccess(String id,Model model){
        Order order = dubboOrderService.selectOrderByOrderId(id);
        model.addAttribute("order",order);
        return "success";
    }


}
