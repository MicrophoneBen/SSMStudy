package com.heshan.service.impl;

import com.heshan.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author HeShan
 * @date 2019/10/5 17:13
 */
@Service
public class UserSeviceImpl implements UserService {


    RedisTemplate<String,String> redisTemplate;
    /**
     * redis string测试
     * 通过某个key得到值
     * 如果key在redis中不存在，到数据库中查询
     * 如果存在，就到redis中查询
     * @param key
     * @return
     */
    @Override
    public String getString(String key) {
        ValueOperations<String, String> string = redisTemplate.opsForValue();

      // redisTemplate.expire("java1802",2, TimeUnit.HOURS);
        redisTemplate.opsForValue().set("java1803","这是一个测试数据",2, TimeUnit.HOURS);

        if(redisTemplate.hasKey(key)){
            //Redis中取出并返回
            System.out.println("在Redis中取出返回");
           return string.get(key);
        }else {
            //查询数据库
            String result = " RedisTemplate模板练习";

            string.set(key,result);
            System.out.println("在MYSQL中取出返回");

            return result;
        }
    }
}
