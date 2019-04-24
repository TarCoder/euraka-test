package com.jt.test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedis {
	
	@Test
	public void testString() throws InterruptedException {
		Jedis jedis = new Jedis("192.168.174.131", 6379);
//		jedis.set("1812","tomcat猫");
//		System.out.println(jedis.get("1812"));
//		jedis.expire("1812", 30);
//		Thread.sleep(2000);
//		System.out.println(jedis.ttl("1812"));
//		jedis.hset("user","id","1");
		String id = jedis.hget("user", "id");
		System.out.println(id);
		Map<String, String> map = jedis.hgetAll("dog");
		System.out.println(map);
		Set<String> keys = jedis.hkeys("dog");
		
		System.out.println(keys);
		String[] arr;
		arr = new String[keys.size()];
		Iterator<String> iterator = keys.iterator();
		for (int i=0;i<arr.length;i++) {
			iterator.hasNext();
			arr[i] = iterator.next();
		}
		System.out.println(arr.toString());
		jedis.hdel("dog",arr);
		Set<String> keys2 = jedis.hkeys("dog");
		System.out.println(keys2);
		jedis.hmset("dog",map);
		map = jedis.hgetAll("dog");
		System.out.println(map);
		
		
		
	}
	
	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.174.131", 6379);
		jedis.lpush("list","1","2","3","4","5");
		String rpop = jedis.rpop("list");
		System.out.println(rpop);
	}
	
	@Test
	public void testList2() {
		Jedis jedis = new Jedis("192.168.174.131", 6379);
		jedis.lpush("list","1","2","3");
		String rpop = jedis.rpop("list");
		System.out.println(rpop);
	}
	
	
	
	
}
