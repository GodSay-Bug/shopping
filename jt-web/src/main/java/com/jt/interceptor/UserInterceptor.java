package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.thread.UserThreadLocal;
import com.jt.util.CookieUtil;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author WL
 * @Date 2020-10-22 9:49
 * @Version 1.0
 * 用户敏感信息拦截器
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisCluster jedisCluster;


    // spring 5 只需要重写需要的方法即可

    /**
     * 需求：/cart的域名都拦截，并校验用户是否登录：1.检查cookie信息；2.检查redis中是否有记录
     * 如果登录：放行
     * 如果未登录：请求应该拦截，重定向到登录界面
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

    // 服务执行前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断cookie中是否有值
        String jt_ticket = CookieUtil.getValue(request, "JT_TICKET");
        // 校验ticket
        if (!StringUtils.isEmpty(jt_ticket)) {
            // 判断redis是否有值
            if (jedisCluster.exists(jt_ticket)) {
                // 动态的获取json的信息
                String userJson = jedisCluster.get(jt_ticket);
                User user = ObjectMapperUtil.toObject(userJson, User.class);
                // 把获取到的user对象传入 域中 ，进而进入controller
//                request.setAttribute("JT_USER", user);
                UserThreadLocal.set(user);
                return true;
            }
        }
        response.sendRedirect("/user/login.html");
        return false;

    }

    /**
     * 使用完数据以后，要销毁数据
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        request.removeAttribute("JT_USER");
        // 销毁数据
        UserThreadLocal.remove();
    }
}