package com.jt.config;

import com.jt.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 这个文件相当于web。xml文件
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    //开启匹配后缀型配置
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 开启后缀类型的匹配   实现 xxx.XX .后面有内容就可
        configurer.setUseSuffixPatternMatch(true);
    }

    @Autowired
    private UserInterceptor interceptor;

    // 	添加拦截器功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //	添加拦截器。拦截器路径 **表示多及目录,交给  自己定义的interceptor处理
        registry.addInterceptor(interceptor).addPathPatterns("/cart/**", "/order/**");

    }
}
