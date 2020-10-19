package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author WL
 * @Date 2020-9-27 12:20
 * @Version 1.0
 */
@TableName("tb_item_cat")
@Data
@Accessors(chain = true)
public class ItemCat extends BasePojo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Boolean isParent;  //数据库进行转化
}
