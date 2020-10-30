package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author WL
 * @Date 2020-10-21 14:41
 * @Version 1.0
 */
@MapperScan("com.jt.mapper")
@SpringBootApplication
public class SpringBootRun {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRun.class, args);
    }
}
