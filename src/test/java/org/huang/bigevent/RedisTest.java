package org.huang.bigevent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTest {
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Test
    public void testRedis() {
        stringRedisTemplate.opsForValue().set("name", "huang");
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
    }
    
}
