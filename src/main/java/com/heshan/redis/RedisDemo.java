package com.heshan.redis;

import com.heshan.bean.User;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HeShan
 * @date 2019/10/4 23:35
 */
public class RedisDemo {
    public static void main(String[] args) {
        //1连接池 Redis POOL 基本信息
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);//最大连接数
        poolConfig.setMaxIdle(1);//最大空闲数
        //..........

        //2连接池
        String host = "192.168.3.8";
        int port = 6379;
        JedisPool pool = new JedisPool(poolConfig,host,port);

        Jedis jedis = pool.getResource();
        jedis.auth("980308");
        System.out.println(jedis.ping());
    }
    /**
     * 测试字符串string
     */
    @Test
    public void t1(){
        //获取jedis连接
        Jedis jedis = new Jedis("192.168.3.8", 6379);
        jedis.auth("980308");

        jedis.set("strName","字符串名称");
        String strName = jedis.get("strName");
        System.out.println("Redis中的数据 = " + strName);
        jedis.close();
    }
    /**
     * Redis String 作用：为了减轻数据库（MYSQL）的访问压力需求，判断某KEY是否存在，如果存在，就在Redis中查询
     * 如果不存在，就查询数据库，且要将查询出的数据存入Redis
     */
    @Test
    public void t2(){
        //获取jedis连接
        Jedis jedis = RedisPoolUtil.getJedis();

        String key = "applicationName";//key名称

        if(jedis.exists(key)){
            String result = jedis.get(key);
            System.out.println("Redis数据库中查询得到" + result);
        }else {
            //数据库中查询
            String result = "微信开发会议达人";
            jedis.set(key,result);
            System.out.println("MYSQL数据库中查询到" + result);
        }

        RedisPoolUtil.close(jedis);
    }

    /**
     * Jedis 完成hash 类型操作
     * 需求：hash存储一个对象
     *    判断Redis中是否存在该key，如果存在，直接返回对应值
     *    如果不存在，查询数据库，（将查询结果存入redis）并返回给用户
     *  
     */
    @Test
    public void t3(){
        Jedis jedis = RedisPoolUtil.getJedis();
         String key = "users";
         
         if(jedis.exists(key)){
             Map<String, String> map = jedis.hgetAll(key);
             System.out.println("Redis数据库中查询结果");
             System.out.println("map = " + map);
         }else {
             //查询数据库并返回结果
             String id = "1";
             String name = "宋凯";
             jedis.hset(key,"id",id);
             jedis.hset(key,"name",name);
             jedis.hset(key,"age","22");
             jedis.hset(key,"remake","这是一位男同学");
             System.out.println("数据库查询的结果" + id + "/t" + name);
         }

        RedisPoolUtil.close(jedis);
    }
    /**
     * 对上面方法进行优化
     * User
     */
    @Test
    public void t4(){
        //User selectById(String id)
        Jedis jedis = RedisPoolUtil.getJedis();

        int id  = 20;
        String key = User.getKeyName() + id;//user:1

        if(jedis.exists(key)){
            //redis读取该对象
            Map<String, String> map = jedis.hgetAll(key);

            User user = new User();
            user.setId(Integer.parseInt(map.get("id")));
            user.setName(map.get("name"));
            user.setAge(Integer.parseInt(map.get("age")));
            user.setPassword(map.get("password"));
            user.setUsername(map.get("username"));

            System.out.println("Redis数据库中查询的对象" + user);
        }else {
            User user = new User();
            user.setId(id);user.setName("王五");user.setAge(22);user.setPassword("这是一个？号");user.setUsername("xu");

            Map<String,String> hash = new HashMap<String, String>();
            hash.put("id",user.getId() + "");
            hash.put("name",user.getName());
            hash.put("age",user.getAge()+ "");
            hash.put("password",user.getPassword());
            hash.put("username",user.getUsername());
            jedis.hmset(key,hash);

            System.out.println("MYSQL查询到的User对象是" + user);
        }

        RedisPoolUtil.close(jedis);
    }
}
