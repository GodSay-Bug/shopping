package com.jt.aop;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @Author WL
 * @Date 2020-9-28 15:34
 * @Version 1.0
 * 定义全局异常通知类
 */

/**
 * 拦截controller层
 */

@RestControllerAdvice       // 也是一个controller，能获取request的值
public class SystemAop {

    /**
     * 跨域请求：JSONP要求返回值的类型是 callback（JSON）结构，
     * 如果url中携带callback参数，就是跨域
     */
    //  定义全局异常方法
    @ExceptionHandler({RuntimeException.class})
    public Object systemAop(Exception e, HttpServletRequest request) {
        //  获取callback参数
        String callback = request.getParameter("callback");
        e.printStackTrace();
        if (StringUtils.isEmpty(callback)) {    //  常规请求
            return SysResult.fail();
        } else {
            return new JSONPObject(callback, SysResult.fail());     // 跨域请求
        }


    }


}
