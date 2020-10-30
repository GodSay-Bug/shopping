package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author WL
 * @Date 2020-10-21 15:09
 * @Version 1.0
 */
@Controller
@RequestMapping("/cart/")
public class CartController {

    @Reference(check = false, timeout = 3000)
    private DubboCartService dubboCartService;

    /**
     * 购物车页面展现
     * http://www.jt.com/cart/show.html
     *
     * @param model
     * @return
     */
    @RequestMapping("show")
    public String addCart(Model model, HttpServletRequest request) {
//        User user = (User) request.getAttribute("JT_USER");
        User user = UserThreadLocal.get();
        Long userId = (long) user.getId();

        List<Cart> cartList = dubboCartService.findCartListByUserId(userId);
        model.addAttribute("cartList", cartList);
        return "cart";
    }


    /**
     * http://www.jt.com/cart/update/num/562379/13
     *
     * @param itemId
     * @param num
     * @param userId
     */
    @ResponseBody //1.值转换为json 2.ajax结束标志
    @RequestMapping("update/num/{itemId}/{num}")
    //public void changeNum(@PathVariable Long itemId, @PathVariable Integer num) {     key的名称和属性的名称一致时,可用对象接收
    public void changeNum(Cart cart, HttpServletRequest request) {
        // 从UserInterceptor中保存在域中的独享中获取user信息
//        User user = (User) request.getAttribute("JT_USER");
        //  使用线程为基准的，从threadlocal中再取出数据
        User user = UserThreadLocal.get();
        Long userId = (long) user.getId();
        cart.setId(userId);
        dubboCartService.updateNum(cart);
    }

    /**
     * 完成购物车新增
     * http://www.jt.com/cart/add/562379.html
     *
     * @param Cart对象 return 重定向到购物车页面
     */

    @RequestMapping("add/{itemId}")
    public String addCart(Cart cart, HttpServletRequest request) {
//        User user = (User) request.getAttribute("JT_USER");
        User user = UserThreadLocal.get();
        Long userId = (long) user.getId();

        cart.setUserId(userId);
        dubboCartService.addCart(cart);

        return "redirect:/cart/show.html";
    }


}
