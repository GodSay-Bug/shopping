package com.jt;

import com.jt.config.JedisConfig;
import com.jt.util.ObjectMapperUtil;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;
import redis.clients.jedis.util.ShardInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author WL
 * @Date 2020-10-12 17:17
 * @Version 1.0
 */
public class TestRedis {


    /**
     * 数据会保存在哪个缓存中呢？
     *  一致性哈希算法解决分布式缓存
     *
     */
    @Test
    public void shardedRedisTests(){

        List<JedisShardInfo> list = new ArrayList<>();
        list.add(new JedisShardInfo("192.168.126.129",6379));
        list.add(new JedisShardInfo("192.168.126.129",6380));
        list.add(new JedisShardInfo("192.168.126.129",6381));
        // 准备分片对象
        ShardedJedis shardedJedis = new ShardedJedis(list);
        shardedJedis.set("aa", "asdasdasdd");

    }

    @Test
    public void tsess(){
        Integer i = new Integer(22);
        Integer i2 = new Integer(144);
        int i3 = 144;
        int i4 = 22;
        Integer i5 = Integer.valueOf(144);
        Integer i6 = Integer.valueOf(22);

        System.out.println(i==i4);
        System.out.println(i2.equals(i3));
        System.out.println(i2==i3);
        System.out.println(i2==i5);
    }


    @Test
    public void test1() throws InterruptedException {
        Jedis jedis = new Jedis("192.168.126.129",6379);
        jedis.set("redis", "添加一个String-redis");
        System.out.println(jedis.exists("redis"));

        String redis = jedis.get("redis");
        System.out.println(redis);
    }


    @Test
    public void test2() throws InterruptedException {
        Jedis jedis = new Jedis("192.168.126.129",6379);

        jedis.setex("redis", 50, "添加一个String");
        Thread.sleep(1111);
        System.out.println(jedis.ttl("redis"));
        //  可能不能保持原子性
       /* jedis.expire("redis", 60);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(jedis.ttl("redis"));*/

    }

    @Test
    public void test3() {
        Jedis jedis = new Jedis("192.168.126.129",6379);
        jedis.setnx("redis", "添加一个String-redis");   // 取代if-else，当key存在时不修改
    }

    /**
     *  同时要求原子性和设定超时时间时
     */
    @Test
    public void test4() {
        Jedis jedis = new Jedis("192.168.126.129",6379);
        SetParams set = new SetParams();
        set.nx().ex(20);    // 加锁
        jedis.set("a", "bbbaabb", set);
        System.out.println(jedis.get("a"));
        jedis.del("a"); // 解锁
    }


    /*
    事务
     */
    @Test
    public void test5(){
        Jedis jedis = new Jedis("192.168.126.129",6379);
        Transaction tran = jedis.multi();
        try{
            tran.set("we", "sadsads");
            tran.set("sdsad","sdasdasas");
            int i = 10/0;
            tran.exec();
        }catch (Exception e){
            e.printStackTrace();
            tran.discard();
        }finally {
            tran.close();
        }

        System.out.println(jedis.get("we"));


    }

}
