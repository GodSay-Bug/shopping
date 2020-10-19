package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.config.JedisConfig;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author WL
 * @Date 2020-9-27 14:07
 * @Version 1.0
 */



@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired(required = false)    //保证后续操作的正常执行  可以懒加载
    private Jedis jedis;

    @Override
    public String doGetCatName(Long id) {
        ItemCat itemCat = itemCatMapper.selectById(id);

        String name = itemCat.getName();
        return name;
    }

    //  数据格式
    /*
    [
	{
		"id":"1",
		"text":"英雄联盟",
		"iconCls":"icon-save",
		"children":[
			{
				"id":"4",
				"text":"沙漠死神"
			},{
				"id":"5",
				"text":"德玛西亚"
			},{
				"id":"6",
				"text":"诺克萨斯之手"
			},
			{
				"id":"7",
				"text":"蛮族之王"
			},
			{
				"id":"8",
				"text":"孙悟空"
			}
		],
		"state":"open"
	},{
		"id":"2",
		"text":"王者荣耀",
		"children":[
			{
				"id":"10",
				"text":"阿科"
			},{
				"id":"11",
				"text":"吕布"
			},{
				"id":"12",
				"text":"陈咬金"
			},{
				"id":"13",
				"text":"典韦"
			}
		],
		"state":"closed"
	},
	{
		"id":"3",
		"text":"吃鸡游戏",
		"iconCls":"icon-save"
	}
]

     */

    @Override
    public List<EasyUITree> findTree(Long parentId) {
        //  查询数据库记录
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);

        List<ItemCat> itemCats = itemCatMapper.selectList(queryWrapper);
        //  树的格式是数组形式
        //  转化为List《VO》
        List<EasyUITree> lists = new ArrayList<>();
        //  拿一个封装一个EasyUITree对象
        for (ItemCat it : itemCats) {
            Long id = it.getId();
            String text = it.getName();
            //  如果是父级，就先关闭
            String state = it.getIsParent()? "closed" : "open";

            EasyUITree et = new EasyUITree(id, text, state);
            lists.add(et);
        }
        return lists;
    }

    /**
     * redis缓存
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITree> findItemCatListCatch(Long parentId) {

        List<EasyUITree> treeLists = new ArrayList<>();
        String key = "ITME_CAT_PATENTID::"+parentId;

        if (jedis.exists(key)){     // 如果缓存存在
            String json = jedis.get(key);
            // 从redis中查询的数据是json格式的，返回的是List类型的对象，所以要转换类型
            treeLists = ObjectMapperUtil.toObject(json, treeLists.getClass());
            System.out.println(treeLists);
            System.out.println("缓存");

        }else{  //  不存在缓存就查询数据库
            treeLists = findTree(parentId);
            //   查询数据库的后要把数据保存在redis，所以要把对象转为json格式
            String json = ObjectMapperUtil.toJSON(treeLists);
            jedis.set(key,json);
            System.out.println("查询数据库");
        }
        return treeLists;
    }
}