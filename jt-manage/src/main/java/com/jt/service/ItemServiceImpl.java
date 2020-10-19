package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.PageItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;

    @Override
    public ItemDesc selectDescById(Long item_id) {
        return itemDescMapper.selectById(item_id);
    }

    @Override
    public List<Item> getItems() {
        return itemMapper.selectList(null);
    }


    //  上下架
    @Override
    public void instockItem(Integer status, Long[] ids) {
        Item item = new Item();
        UpdateWrapper<Item> uw = new UpdateWrapper<>();
        List<Long> list = Arrays.asList(ids);
        uw.set("status", status).in("id", list);
        itemMapper.update(item, uw);
    }

    //  上架
//    @Override
//    public void reshelfItem(Long[] ids) {
//        Item item = new Item();
//        for (Long id : ids) {
//            UpdateWrapper<Item> uw = new UpdateWrapper<>();
//            uw.set("status", 1).eq("id", id);
//            itemMapper.update(item, uw);
//        }
//    }
    // 删除
    @Transactional
    @Override
    public void deleteItems(Long[] ids) {
        List<Long> longs = Arrays.asList(ids);
        itemMapper.deleteBatchIds(longs);
        itemDescMapper.deleteBatchIds(longs);
//        for (Long id:ids) {
//            itemMapper.deleteById(id);
//        }
    }

    @Transactional
    @Override
    public void updateItem(Item item, ItemDesc itemDesc) {
        itemMapper.updateById(item);
        itemDesc.setItemId(item.getId());
        itemDescMapper.updateById(itemDesc);
    }


    @Override
    @Transactional  //  控制事务
    public int insertItem(Item item, ItemDesc itemDesc) {   //id=null
        item.setStatus(1);
        int rows = itemMapper.insert(item);

        //  MP的方式会自动实现主键的回显 use
        itemDesc.setItemId(item.getId());
        itemDescMapper.insert(itemDesc);
        return rows;
    }

    // 数据格式

    /*
    {
	"total":2000,
	"rows":[
		{"code":"A","name":"果汁","price":"20"},
		{"code":"B","name":"汉堡","price":"30"},
		{"code":"C","name":"鸡柳","price":"40"},
		{"code":"D","name":"可乐","price":"50"},
		{"code":"E","name":"薯条","price":"10"},
		{"code":"F","name":"麦旋风","price":"20"},
		{"code":"G","name":"套餐","price":"100"}
	]
    }
     */

    /**
     * MP方式
     *
     * @param page 当前页
     * @param rows 数据条数
     * @return pageItem 页面数据
     */
    @Override
    public PageItem<Item> findAll(Integer page, Integer rows) {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("updated");
        // Ipage接口 			实现类封装了（当前页，条数）
        IPage<Item> iPages = new Page<Item>(page, rows);
        IPage<Item> itemIPage = itemMapper.selectPage(iPages, queryWrapper);
        //	分页的总记录数
        Long total = itemIPage.getTotal();
        List<Item> list = itemIPage.getRecords();

        PageItem<Item> pageItem = new PageItem<>(total, list);

        return pageItem;
    }


//	@Override
//	public PageItem<Item> findAll(Integer page,Integer rows) {
//
//
//		List<Item> list = itemMapper.findAll(page, rows);
//		Long total = Long.valueOf(itemMapper.selectCount(null));
//
//		PageItem<Item> pageItem = new PageItem<Item>(total,list);
//		return pageItem;
//	}


}
