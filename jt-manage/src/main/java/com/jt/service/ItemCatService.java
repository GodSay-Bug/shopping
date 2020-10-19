package com.jt.service;

import com.jt.vo.EasyUITree;

import java.util.List;

/**
 * @Author WL
 * @Date 2020-9-27 14:07
 * @Version 1.0
 */
public interface ItemCatService {

    String doGetCatName(Long id);

    List<EasyUITree> findTree(Long parentId);

    List<EasyUITree> findItemCatListCatch(Long parentId);
}
