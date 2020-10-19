package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author WL
 * @Date 2020-10-13 20:17
 * @Version 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CacheFind {

    String key();   // 用户标识key的前缀
    int seconds() default 0;    //  超时时间

}
