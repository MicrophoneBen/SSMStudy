package com.heshan.redisTemplate;

import com.heshan.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

/**
 * @author HeShan
 * @date 2019/10/5 17:24
 */
public class RedisTemplateTest {
    /**
     * 测试string  RedisTemplate
     */
    @Test
    public void t1(){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("springredis/springredis.xml");
        UserService userService = ctx.getBean(UserService.class);

        String key = "applicationName";
        String result = userService.getString(key);
        System.out.println(result);
    }
}
