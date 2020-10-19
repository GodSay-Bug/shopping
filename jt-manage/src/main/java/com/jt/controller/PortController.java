package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author WL
 * @Date 2020-9-30 12:53
 * @Version 1.0
 * 动态获取当前的服务器端口
 */
@RestController
public class PortController {

    @Value("${server.port}")
    private int port;

    @RequestMapping("/getPort")
    public String getPort(){
        return "当前端口号："+port;
    }

}
