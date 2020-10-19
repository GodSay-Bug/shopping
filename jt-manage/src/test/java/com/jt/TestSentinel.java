package com.jt;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author WL
 * @Date 2020-10-15 16:01
 * @Version 1.0
 */


public class TestSentinel {

    @Test
    public void sentinelTests() {

        Set<String> set = new HashSet<String>();
        set.add("192.168.126.129:26379");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", set);
        Jedis jedis = jedisSentinelPool.getResource();
        jedis.set("test", "测试烧饼");
        System.out.println(jedis.get("test"));


    }


}
