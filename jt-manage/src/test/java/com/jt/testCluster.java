package com.jt;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author WL
 * @Date 2020-10-16 11:13
 * @Version 1.0
 */
public class testCluster {


    /**
     * redis集群测试
     */
    @Test
    public void tests() {

        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.126.129", 7000));
        nodes.add(new HostAndPort("192.168.126.129", 7001));
        nodes.add(new HostAndPort("192.168.126.129", 7002));
        nodes.add(new HostAndPort("192.168.126.129", 7003));
        nodes.add(new HostAndPort("192.168.126.129", 7004));
        nodes.add(new HostAndPort("192.168.126.129", 7005));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("qwe", "qwewqe");
        System.out.println(jedisCluster.get("qwe"));


    }

}
