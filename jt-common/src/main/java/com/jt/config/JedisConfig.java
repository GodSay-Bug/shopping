package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.*;

import java.util.*;

/**
 * @Author WL
 * @Date 2020-10-13 14:06
 * @Version 1.0
 * redis分片机制
 */
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class JedisConfig {


    @Value("${redis.sentinel}")
    private String sentinel;

    @Value("${redis.nodes}")
    private String nodes;

    @Value("${redis.cluster}")
    private String cluster;

    @Bean
    public JedisCluster jedisCluster() {

        Set<HostAndPort> set = new HashSet<>();
        String[] nodesArray = cluster.split(",");
        for (String node : nodesArray) {
            String url = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            set.add(new HostAndPort(url, port));
        }
        return new JedisCluster(set);
    }


    /**
     * 哨兵池
     *
     * @return
     */
  /*  @Bean
    public JedisSentinelPool jedisSentinelPool() {

        Set<String> sentinels = new HashSet<>();
        sentinels.add(sentinel);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(40);
        jedisPoolConfig.setMaxIdle(20);
        jedisPoolConfig.setMinIdle(10);
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels);
        return jedisSentinelPool;
    }
*/

    /**
     * redis分片机制
     *
     * @return
     */
    @Bean
    public ShardedJedis shardedJedis() {
        nodes = nodes.trim();
        String[] split = nodes.split(",");

        List<JedisShardInfo> lists = new ArrayList<>();
        for (String node : split) {
            String ip = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            lists.add(new JedisShardInfo(ip, port));
        }
        return new ShardedJedis(lists);
    }

  /*  //  redis单台
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;

    @Bean
    public Jedis jdeis() {
        return new Jedis(host, port);
    }
*/

}
