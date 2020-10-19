package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author WL
 * @Date 2020-10-17 9:59
 * @Version 1.0
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
public class User extends BasePojo {
    /**
     * id                   bigint not null auto_increment,
     * username             varchar(50),
     * password             varchar(32) comment 'MD5加密',
     * phone                varchar(20),
     * email                varchar(50),
     * created              datetime,
     * updated              datetime,
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;   // 暂时用电话代替邮箱

}
