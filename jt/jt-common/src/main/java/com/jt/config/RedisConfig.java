package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jt.properties.RedisProperties;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

//该配置为配置类,代替之前配置文件web.xml和spring配置文件
@Configuration //表示配置类,当springboot程序启动时会加载配置类
//@ImportResource({"classpath:/spring/application-redis.xml"}) //导入第三方的配置文件
//@PropertySource("classpath:/properties/redis.properties") //导入properties文件
public class RedisConfig {
	
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> set = new HashSet<>();
		String[] split = RedisProperties.node.split(",");
		for (String str : split) {
			String[] split2 = str.split(":");
			HostAndPort hap = new HostAndPort(split2[0], Integer.parseInt(split2[1]));
			set.add(hap);
		}
		JedisCluster jedisCluster = new JedisCluster(set);
		return jedisCluster;
	}
	
	@Bean
	public Jedis jedis() {
		return new Jedis(RedisProperties.host,RedisProperties.port01);
	}
	
//	@Bean
//	public ShardedJedis shardedJedis() {
//		List<JedisShardInfo> shards = new ArrayList<>();
//		shards.add(new JedisShardInfo(RedisProperties.host,RedisProperties.port01));
//		shards.add(new JedisShardInfo(RedisProperties.host,RedisProperties.port02));
//		shards.add(new JedisShardInfo(RedisProperties.host,RedisProperties.port03));
//		String[] split = RedisProperties.redisShards.split(",");
//		for (String str : split) {
//			String[] split2 = str.split(":");
//			JedisShardInfo jedisShardInfo = new JedisShardInfo(split2[0],Integer.valueOf(split2[1]));
//			shards.add(jedisShardInfo);
//		}
//		ShardedJedis shardedJedis = new ShardedJedis(shards);
//		return shardedJedis;
//	}
//	
//	@Bean
//	public JedisSentinelPool jedisSentinelPool() {
//		Set<String> sentinels = new HashSet<>();
//		sentinels.add(RedisProperties.nodes);
//		JedisSentinelPool pool = new JedisSentinelPool(RedisProperties.masterName, sentinels);
//		return pool;
//	}
}
