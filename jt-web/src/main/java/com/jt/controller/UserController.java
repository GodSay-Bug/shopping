package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.CookieUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author WL
 * @Date 2020-10-17 9:38
 * @Version 1.0
 * 服务消费者
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Reference(check = false, timeout = 3000)
    private DubboUserService dubboUserService;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * restFul方式：动态获取url中的参数，设置通用跳转
     * @param moudel
     * @return
     */
    @RequestMapping("{moudel}")
    public String doLogin(@PathVariable String moudel) {
        return moudel;
    }

    /**
     * 微服务：dubbo
     * 实现用户信息的注册：
     * http://www.jt.com/user/doRegister
     * password: admin123
     * username: admin123123
     * phone: 13525313942
     * return SysResult
     */
    @ResponseBody
    @RequestMapping("doRegister")
    public SysResult doSave(User user) {
        // 消费者基于dubbo协议将user对象进行网络数据传输，所以对象必须序列化
        dubboUserService.doSave(user);
        return SysResult.success();

    }

    /**
     * 实现用户登录
     * @param username
     * @param password
     * @return SysResult
     * <p>
     * cookie的使用
     * 实现步骤:
     * 1.当用户输入用户名和密码点击登录时,将请求发送给JT-WEB消费者服务器.
     * 2.JT-WEB服务器将用户信息传递给JT-SSO单点登录系统完成数据校验.
     * 3.如果登录成功,则动态生成密钥信息,将user数据转化为json.保存到redis中. 注意超时时间的设定.
     * 4.JT-SSO将登录的凭证 传给JT-WEB服务器.
     * 5.JT-WEB服务器将用户密钥TICKET信息保存到用户的cookie中 注意超时时间设定.
     * 6.如果登录不成功,则直接返回错误信息即可.
     */
    @ResponseBody
    @RequestMapping("doLogin")
    public SysResult doLogin(User user, HttpServletResponse httpServletResponse) {

        String ticket = dubboUserService.doLogin(user);
        if (StringUtils.isEmpty(ticket)) {
            // 说明用户名或密码错误
            return SysResult.fail();
        } else {
          /*  Cookie cookie = new Cookie("JT_TICKET", ticket);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 单位秒 存活时间
            // 该Cookie的使用路径。如果设置为/path/，则只有路径为/path/的页面可以访问该Cookie。如果设置为/，则本域名下的所有页面都可以访问该Cookie。
            cookie.setPath("/");    // 设定cookie有效范围，一般都是/，
            cookie.setDomain("jt.com");     // 设定cookie共享的域名
            httpServletResponse.addCookie(cookie);*/

            CookieUtil.addCookie(httpServletResponse, "JT_TICKET", ticket, 7 * 24 * 60 * 60, "jt.com");

            //  用户登录成功
            return SysResult.success();
        }
    }


    /**
     * 实现用户退出:
     * http://www.jt.com/user/logout.html
     *
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("logout")
    public String doLogout(HttpServletResponse response, HttpServletRequest request) {
        //  根据“JT_TICKET”在cookie中查找ticket
        String ticket = CookieUtil.getValue(request, "JT_TICKET");
        if (!StringUtils.isEmpty(ticket)) {
            jedisCluster.del(ticket);
            CookieUtil.deleteCookie(response, "JT_TICKET", "jt.com");
        }
        return "redirect:/";

    }


}
