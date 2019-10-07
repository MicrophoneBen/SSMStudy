package com.heshan.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author HeShan
 * @date 2019/10/5 15:53
 */
public class RedisPoolUtil {
    private static JedisPool pool;
    static {
        //1连接池 Redis POOL 基本信息
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);//最大连接数
        poolConfig.setMaxIdle(1);//最大空闲数
        //..........
        //2连接池
        String host = "192.168.3.8";
        int port = 6379;
       pool = new JedisPool(poolConfig,host,port);
    }
    public static Jedis getJedis(){
        Jedis jedis = pool.getResource();
        jedis.auth("980308");
        return jedis;
    }
    //关闭连接功能
    public static void close(Jedis jedis){
        jedis.close();
    }
}
