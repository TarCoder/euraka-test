package com.jt.test;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.config.RedisConfig;

import redis.clients.jedis.Jedis;
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class TestSpring {
	
	@Autowired
	private Jedis jedis;
	
	@SuppressWarnings("resource")
	@Test
	public void testJedis() {
		AnnotationConfigApplicationContext con = new AnnotationConfigApplicationContext(RedisConfig.class);
		jedis = con.getBean(Jedis.class);
		jedis.set("1812", "spring整合redis成功");
		System.out.println(jedis.get("1812"));
	}
	
	@Test
	public void testMap() {
		Map<String,String> map = new HashMap<>();
		map.put(null, "123");
		String string = map.get(null);
		System.out.println(string);
	}
	
	
}
