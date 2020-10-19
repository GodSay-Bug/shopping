package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author WL
 * @Date 2020-9-28 19:13
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_item_desc")
public class ItemDesc extends BasePojo{

    @TableId    //  这个地方只标识
    private Long itemId;
    private String itemDesc;

}
