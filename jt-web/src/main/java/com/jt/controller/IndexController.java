package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author WL
 * @Date 2020-10-16 17:06
 * @Version 1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {

    /**
     * 此时网页的伪静态   访问index.html并不能实现/
     * 加入MvcConfigurer.java 后开启后缀类型匹配 才行
     */
    @RequestMapping("index")
    public String index() {
        return "index";
    }


}
