package com.jt.controller;

import com.jt.annotation.CacheFind;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author WL
 * @Date 2020-9-27 14:09
 * @Version 1.0
 */
@RequestMapping("/item/cat/")
@RestController
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;


    @GetMapping("queryItemName")
    @CacheFind(key="ITEM_CAT_NAME" ,seconds = 44)
    public String doFindCatName(Long itemCatId) {

        return itemCatService.doGetCatName(itemCatId);
    }

    /**
     * 根据parentId查子目录
     * 参数：id
     * 返回值：List<EasyUITree>
     * @return  菜单数据
     */
    @CacheFind(key="ITEM_CAT_PARENTID")
    @RequestMapping("list")                  //方式2：@RequestParam
    public List<EasyUITree> doFindTree(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        //  方式1：
        //  初始化的时候id值传入的是空，
        //  点击一级目录的时候回才会传入一级目录的ID，再把此ID当做parentId查询子菜单
//        Long parentId = (id==null)?0:id;
        return  itemCatService.findTree(parentId);

        //return itemCatService.findItemCatListCatch(parentId);
    }
}
