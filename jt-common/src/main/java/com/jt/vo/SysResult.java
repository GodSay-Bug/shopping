package com.jt.vo;

import com.jt.pojo.BasePojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author WL
 * @Date 2020-9-28 11:25
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class SysResult extends BasePojo{


    //  请求状态信息
    private Integer status;
    //  服务器向客户端发提示信息
    private String msg;
    //  返回数据
    private Object data;

    public static SysResult fail(){
        return new SysResult(201,"服务器调试失败",null);
    }

    public static SysResult success(){

        return new SysResult(200,"业务执行成功",null);
    }

    public static SysResult success(Object data){

        return new SysResult(200,"业务执行成功",data);
    }
    public static SysResult success(String msg,Object data){

        return new SysResult(200,msg,data);
    }


}
