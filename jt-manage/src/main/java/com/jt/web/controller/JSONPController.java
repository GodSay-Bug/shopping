package com.jt.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author WL
 * @Date 2020-10-17 14:35
 * @Version 1.0
 */
@RestController
public class JSONPController {

    /**
     * 实现JSONP的跨域请求 JSONP入门API
     * http://manage.jt.com/web/testJSONP
     * <p>
     * return：callback(JSON)
     */
   /* @RequestMapping("/web/testJSONP")
    public String testJSONP(String callback){
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(12211L).setItemDesc("测试JSONP");
        String json = ObjectMapperUtil.toJSON(itemDesc);
        return callback+"("+json+")";
    }*/


    /**
     * JSONP
     * @param callback
     * @return
     */
    @RequestMapping("/web/testJSONP")
    public JSONPObject testJSONP(String callback) {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(12211L).setItemDesc("测试JSONP");
        return new JSONPObject(callback, itemDesc);
    }

}
