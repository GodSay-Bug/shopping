package com.jt.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.User;

import java.util.List;

public interface UserService{

    List<User> findUserAll();

    boolean checkUser(String param, Integer type);
}
