package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.PageItem;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jt.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/item/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Request URL: http://localhost:8091/item/query/item/desc/1474391959
     * @param itemId
     * @return
     */
    @RequestMapping("query/item/desc/{itemId}")
    public SysResult getDescById(@PathVariable Long itemId){
        ItemDesc itemDesc = itemService.selectDescById(itemId);
        return SysResult.success(itemDesc);
    }

    /**
     * Request URL: http://localhost:8091/item/update
     * @param item
     * @return
     */
    @RequestMapping("update")
    public SysResult doUpdate(Item item,ItemDesc itemDesc){

        itemService.updateItem(item,itemDesc);
        return SysResult.success();
    }


    /**
     * 删除
     * URL: http://localhost:8091/item/delete
     */
    @RequestMapping("delete")
    public SysResult doDelete(Long[] ids){
        itemService.deleteItems(ids);
        return SysResult.success();

    }


    /**
     * 查询页面数据
     * url:localhost:8091/item/query?page=1&rows=20
     * 请求参数：page=1&rows=20
     *
     * @return
     */
    @GetMapping("query")
    public PageItem<Item> doFindAll(Integer page, Integer rows) {    // 从request请求中获取页码数据

        //	easyUI在启用分页时，会自动拼接两个参数 page:当前页码    rows:每页显示行数 名字固定
//		int page = Integer.parseInt(request.getParameter("page"));
//		int rows = Integer.parseInt(request.getParameter("rows"));

        PageItem<Item> all = itemService.findAll(page, rows);
        return all;
    }

    /*
    cid: 163
    title: 1
    sellPoint: 1
    priceView: 1.00
    price: 100
    num: 1
    barcode: 1
    image:
    itemDesc: 1
    itemParams: []
     */

    /**
     * 添加商品
     *
     * @param item
     * @return SysResult
     */
    @RequestMapping("save")
    public SysResult doSave(Item item, ItemDesc itemDesc) {
//        try {
//            itemService.insertItem(item);
//            return SysResult.success();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return SysResult.fail();
//        }

        itemService.insertItem(item,itemDesc);
        return SysResult.success();
    }

    /**
     * url: http://localhost:8091/item/instock
     *
     * @param ids
     * @return
     */
    @RequestMapping("updateState/{status}")
    public SysResult doInstock(@PathVariable Integer status,Long[] ids) {
        itemService.instockItem(status,ids);
        return SysResult.success();
    }

    /**
     * http://localhost:8091/item/reshelf
     *
     * @param ids
     * @return
     */
//    @RequestMapping("reshelf")
//    public SysResult doReshelf(Long[] ids) {
//        itemService.reshelfItem(ids);
//        return SysResult.success();
//    }


}
