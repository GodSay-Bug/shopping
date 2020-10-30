package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.DubboItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author WL
 * @Date 2020-10-21 11:34
 * @Version 1.0
 */
@Controller
public class ItemController {

    @Reference(check = false, timeout = 3000)
    private DubboItemService dubboItemService;

    /**
     * 返回商品页面
     * http://www.jt.com/items/562379.html
     *
     * @param itemId
     * @param model
     * @return item.jsp
     */

    @RequestMapping("/items/{itemId}")
    public String findItemById(@PathVariable Long itemId, Model model) {
        Item item = dubboItemService.findItemById(itemId);
        ItemDesc itemDesc = dubboItemService.findItemDescById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        return "item";
    }






}
