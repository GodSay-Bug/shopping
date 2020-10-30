package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

/**
 * @Author WL
 * @Date 2020-10-20 14:10
 * @Version 1.0
 * 服务提供者的实现类
 */

@Service
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 用户注册Impl
     * @param user
     */
    @Override
    public void doSave(User user) {
        // 加密密码
        String psw = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setEmail(user.getEmail())
                .setPassword(psw);
        userMapper.insert(user);

    }

    /**
     * 用户登录Impl
     * 1.获取用户信息校验数据库中是否有记录
     * 2.有  开始执行单点登录流程
     * 3.没有 直接返回null即可
     * @param user
     * @return
     */
    @Override
    public String doLogin(User user) {
        // 密码加密
        String md5Psw = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Psw);
        QueryWrapper<User> qw = new QueryWrapper<>(user);
        // 查找数据
        User userDB = userMapper.selectOne(qw);
        if (userDB == null) {  // 如果数据不存在
            return null;
        } else {
            // 开始单点登录
            String ticket = UUID.randomUUID().toString();
            userDB.setPassword("傻逼");
            String result = ObjectMapperUtil.toJSON(userDB);
            jedisCluster.setex(ticket, 7 * 24 * 60 * 60, result);
            return ticket;
        }
    }
}
