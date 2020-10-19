package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.PageItem;

import java.util.List;

public interface ItemService {
    

    PageItem<Item> findAll(Integer page, Integer rows);

    int insertItem(Item item, ItemDesc itemDesc);

    void instockItem(Integer status,Long[] ids);

//    void reshelfItem(Long[] ids);

    void deleteItems(Long[] ids);

    void updateItem(Item item, ItemDesc itemDesc);

    ItemDesc selectDescById(Long itemId);

    List<Item> getItems();
}
