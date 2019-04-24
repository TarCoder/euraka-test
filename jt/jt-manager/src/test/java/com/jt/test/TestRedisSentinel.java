package com.jt.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestRedisSentinel {
	
	@Test
	public void test01() {
		String string = new HostAndPort("192.168.174.131", 26379).toString();
		System.out.println("工具API的结果:"+string);
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.174.131:26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("1812", "哨兵测试完成!");
		String string2 = jedis.get("1812");
		System.out.println("测试结果:"+string2);
		pool.close();
	}
}
