package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

/**
 * @Author WL
 * @Date 2020-10-21 11:39
 * @Version 1.0
 */
public interface DubboItemService {

    Item findItemById(Long itemId);

    ItemDesc findItemDescById(Long itemId);
}
