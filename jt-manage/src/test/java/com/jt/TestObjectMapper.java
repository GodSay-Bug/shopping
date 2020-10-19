package com.jt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author WL
 * @Date 2020-10-13 14:39
 * @Version 1.0
 */
public class TestObjectMapper {

    /**
     * 简单对象的转换
     * ObjectMapper实现对象与json之间的转换
     *
     * @throws JsonProcessingException
     */
    @Test
    public void test01() throws JsonProcessingException {
        ObjectMapper ob = new ObjectMapper();
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(11111L).setItemDesc("盖盖盖盖盖盖盖亚").setCreated(new Date()).setUpdated(new Date());
        String r = ob.writeValueAsString(itemDesc);
        System.out.println(r);

        ItemDesc itemDesc1 = ob.readValue(r, ItemDesc.class);
        System.out.println(itemDesc1);

    }


    @Test
    public void test02() throws JsonProcessingException {
        ObjectMapper ob = new ObjectMapper();
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(11111L).setItemDesc("盖盖盖盖盖盖盖亚").setCreated(new Date()).setUpdated(new Date());
        ItemDesc itemDesc1 = new ItemDesc();
        itemDesc1.setItemId(22222L).setItemDesc("盖盖亚").setCreated(new Date()).setUpdated(new Date());
        List<ItemDesc> list = new ArrayList<>();
        list.add(itemDesc);
        list.add(itemDesc1);

        String r = ob.writeValueAsString(list);
        System.out.println(r);
        System.out.println(ObjectMapperUtil.toObject(r, List.class));
    }

}
