package com.jt.properties;

public interface RedisProperties {
	
	String host = "192.168.174.131";
	
	Integer port01 = 6379;
	
	Integer port02 = 6380;
	
	Integer port03 = 6381;
	
	String redisShards = "192.168.174.131:6379,192.168.174.131:6380,192.168.174.131:6381";
	
	String nodes = "192.168.174.131:26379";
	
	String masterName = "mymaster";
	
	/**
	 * redis集群信息
	 */
	String node = "192.168.174.131:7000,192.168.174.131:7001,192.168.174.131:7002,192.168.174.131:7003,192.168.174.131:7004,192.168.174.131:7005,192.168.174.131:7006,192.168.174.131:7007,192.168.174.131:7008";
}
