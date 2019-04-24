package com.jt;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * 实现步骤
 * 	1、创建HttpClient对象
 * 	2、指定url请求地址
 * 	3、指定请求的方式 get/post
 * 		规则：
 * 			一般查询操作使用get请求
 * 			如果设计数据入库/更新并且数据很大时采用post请求
 * 			涉密的操作采用post
 * 	4、发起请求获取respongse对象
 * 	5、判断请求是否正确，检查状态码是否200
 * 	6、从返回值对象中获取数据（JSON），之后进行转化对象
 */
public class TestHttpClient {
	
	//实现get请求
	@Test
	public void testGet() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "https://item.jd.com/100002996046.html";
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse execute = httpClient.execute(httpGet);
		if(execute.getStatusLine().getStatusCode()==200) {
			System.out.println("请求正确返回！");
			String result = EntityUtils.toString(execute.getEntity());
			System.out.println(result);
		} else {
			System.out.println("请求不正确！");
		}
	}
	
	
	
	
	
	
}
