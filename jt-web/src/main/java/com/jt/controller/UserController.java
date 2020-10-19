package com.jt.controller;

import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author WL
 * @Date 2020-10-17 9:38
 * @Version 1.0
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    /**
     * restFul方式：动态获取url中的参数，设置通用跳转
     * @param moudel
     * @return
     */
    @RequestMapping("{moudel}")
    public String doLogin(@PathVariable String moudel) {
        return moudel;
    }





}
