package com.jt.web.controller;

import com.jt.pojo.Item;
import com.jt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author WL
 * @Date 2020-10-19 16:25
 * @Version 1.0
 */
@RestController
public class HttpClientController {

    @Autowired
    private ItemService itemService;

    /**
     * url请求地址: http://manage.jt.com/getItems
     */
    @RequestMapping("/getItems")
    public List<Item> getItems() {
        return itemService.getItems();
    }
}
