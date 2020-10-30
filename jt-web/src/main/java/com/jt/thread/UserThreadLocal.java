package com.jt.thread;

import com.jt.pojo.User;

/**
 * @Author WL
 * @Date 2020-10-22 11:23
 * @Version 1.0
 * threadlocal通用类
 */

public class UserThreadLocal {

    // 不会影响线程，跟随线程的
    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void set(User user) {
        threadLocal.set(user);
    }

    public static User get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
