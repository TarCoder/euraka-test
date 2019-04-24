package com.jt.properties;

public interface HttpClientProperties {
	
	//最大连接数
	Integer maxTotal = 1000;
	
	//最大并发链接数
	Integer defaultMaxPerRoute = 20;
	
	//创建链接的最大时间
	Integer connectTimeout = 5000;
	
	//链接获取超时时间
	Integer connectionRequestTimeout = 500;
	
	//数据传输最长时间
	Integer socketTimeout = 5000;
	
	//提交时检查链接是否可用
	boolean staleConnectionCheckEnabled = true;

}
