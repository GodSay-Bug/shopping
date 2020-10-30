package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.UserService;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 跨域
     * 完成测试按钮
     * 1.url地址 :findUserAll
     * 2.参数信息: null
     * 3.返回值结果: List<User>
     */
    @RequestMapping("/findUserAll")
    public List<User> findUserAll() {
        return userService.findUserAll();
    }

    /**
     * 业务说明: jt-web服务器获取jt-sso数据 JSONP跨域请求
     * url地址: http://sso.jt.com/user/check/{param}/{type}
     * 参数:    param: 需要校验的数据   type:校验的类型
     * 返回值:  SysResult对象
     * 真实的返回值: callback(SysResult的JSON)
     */
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject checkUser(@PathVariable String param,
                                 @PathVariable Integer type,
                                 String callback) {
        //true 表示数据存在     false 表示数据可以使用
        boolean flag = userService.checkUser(param, type);
        SysResult.success(flag);
        return new JSONPObject(callback, SysResult.success(flag));
    }


    /**
     * 跨域
     * 1.用户通过cookie信息查询用户数据 		通过ticket获取redis中的业务数据
     * http://sso.jt.com/user/query/f2114e7e-0e50-4646-9221-e5a054b46ddd?callback=jsonp1603243319012&_=1603243319079
     * <p>
     * return SysResult
     */

    @RequestMapping("query/{ticket}")
    public JSONPObject findUserByTicket(@PathVariable String ticket, HttpServletResponse response, String callback) {
        String userJSON = jedisCluster.get(ticket);    // user的json数据
        //	user的值可能为空，因为由于空间问题，该数据被清除，或者是数据被篡改
        if (StringUtils.isEmpty(userJSON)) {
            //	如果为空，说明ticket有问题，需要删除cookie信息
           /* Cookie cookie = new Cookie("JT_TICKET", "");
            cookie.setMaxAge(0);
            cookie.setDomain("jt.com");
            cookie.setPath("/");
            response.addCookie(cookie);*/
            CookieUtil.addCookie(response, "JT_TICKET", ticket, 0, "jt.com");
            return new JSONPObject(callback, SysResult.fail());
        } else {
            return new JSONPObject(callback, SysResult.success(userJSON));
        }


    }


}
