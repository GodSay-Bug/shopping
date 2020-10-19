package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import org.apache.ibatis.annotations.Select;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item>{

    @Select("select * from tb_item order by updated desc limit #{page},#{rows} ")
    List<Item> findAll(Integer page, Integer rows);


}
