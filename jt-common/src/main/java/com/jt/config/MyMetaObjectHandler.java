package com.jt.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author WL
 * @Date 2020-9-28 16:13
 * @Version 1.0
 * MP的自动填充配置
 */
@Slf4j
@Component  //将对象交给spring容器管理
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 添加注解后执行@TableField(fill = FieldFill.INSERT)	//入库自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.setInsertFieldValByName("created", date, metaObject);
        this.setInsertFieldValByName("updated", date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("updated", new Date(), metaObject);
    }
}
