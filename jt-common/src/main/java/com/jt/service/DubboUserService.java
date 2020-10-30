package com.jt.service;

import com.jt.pojo.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author WL
 * @Date 2020-10-20 14:08
 * @Version 1.0
 * 提供者的接口
 */
public interface DubboUserService {

    @Transactional
    void doSave(User user);

    String doLogin(User user);
}
