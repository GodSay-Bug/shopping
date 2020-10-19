package com.jt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author WL
 * @Date 2020-10-17 15:38
 * @Version 1.0
 *
 * 配置同源策略解决方法的cros允许策略
 */

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 在后端  配置cors允许策略
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET","POST")      //.allowedMethods() 对跨区请求类型约束
                .allowedOrigins("*")            // 允许那个域名可以跨域
                .allowCredentials(true)         //  请求时是否允许携带cookie
                .maxAge(1800);                  //  请求长连接超时时间
    }
}
