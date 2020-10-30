package com.jt.service;

import com.jt.pojo.Cart;

import java.util.List;

/**
 * @Author WL
 * @Date 2020-10-21 14:32
 * @Version 1.0
 */

public interface DubboCartService {


    List<Cart> findCartListByUserId(Long userId);

    void updateNum(Cart cart);

    void addCart(Cart cart);
}
