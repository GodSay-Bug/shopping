package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author WL
 * @Date 2020-10-21 14:44
 * @Version 1.0
 */
@Service
public class DubboCartServiceImpl implements DubboCartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Cart> lists = cartMapper.selectList(queryWrapper);
        return lists;
    }

    @Override
    public void updateNum(Cart cart) {
        Cart newCart = new Cart();  // 根据新对象中不为空的属性做set
        newCart.setNum(cart.getNum());
        //  根据对象中不为空的属性作为 where key=value
        UpdateWrapper<Cart> uw = new UpdateWrapper<>(cart.setNum(null));
//        UpdateWrapper<Cart> uw = new UpdateWrapper<>();
//        uw.eq("item_id", cart.getItemId()).eq("user_id",cart.getUserId());
        cartMapper.update(newCart, uw);
    }

    /**
     * 如果重复添加商品，则只更新商品数量
     *
     * @param cart
     */
    @Override
    public void addCart(Cart cart) {
        /*
        1.查询是否存在该商品，根据user_id和item_id
         */
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id", cart.getItemId()).eq("user_id", cart.getUserId());
        Cart cartDB = cartMapper.selectOne(queryWrapper);
        if (cartDB == null) {    // 如果为空，说明该商品还未加入购物车
            cartMapper.insert(cart);
        } else { // 商品存在，只增加商品的数量
            // 将商品的已有数量和新添加的数量相加
            Integer newNum = cartDB.getNum() + cart.getNum();
            cart.setNum(newNum);
            cartMapper.updateNum(cart);
        }

    }

}
