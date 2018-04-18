package com.spring.redis;

import com.spring.redis.service.IRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDemoApplicationTests {

	@Autowired
	private IRedisService redis;

	@Test
	public void contextLoads() {
		redis.set("guofeng", "郭锋", 1*60);

		System.out.println(redis.get("guofeng", String.class));
	}

}
