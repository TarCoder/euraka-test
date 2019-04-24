package com.jt.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientUtil {
	
	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig requestConfig;//封装了请求的超市时间
	
	/**
	 * 为了用户请求方便，封装doGet方法
	 * 参数说明：
	 * 	1、String url地址
	 * 	2、Map<String,String>集合封装参数
	 * 	3、字符集编码
	 */
	public String doGet(String url,Map<String,String> params,String charset) {
		String result = null;
		//1、判断字符集编码是否为null
		if(StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		//2、判断用户是否传递参数
		if(params != null) {
			//如果参数不为null，则需要url地址拼串
			url = url + "?";
			//遍历map集合获取参数
			for (Entry<String, String> entry : params.entrySet()) {
				url += entry.getKey() + "=" + entry.getValue() + "&";
			}
			url = url.substring(0,url.length()-1);//sunstring截串，包头不包尾
		}
		//3、封装请求对象HttpGet
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		//4、发起http请求
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == 200) {
				//如果返回值的状态信息是200，表明请求正确
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			System.out.println("=================================================");
			e.printStackTrace();
			System.out.println("*************************************************");
			throw new RuntimeException();
		}
		return result;
	}
	
	public String doGet(String url) {
		return doGet(url,null,null);
	}
	
	public String doGet(String url,Map<String,String> params) {
		return doGet(url,params,null);
	}
	
	public String doGet(String url,String charset) {
		return doGet(url,null,charset);
	}
	
	/**
	 * 为了用户请求方便，封装doPost方法
	 * 参数说明：
	 * 	1、String url地址
	 * 	2、Map<String,String>集合封装参数
	 * 	3、字符集编码
	 * @throws UnsupportedEncodingException 
	 */
	public String doPost(String url,Map<String,String> params,String charset) {
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		//1、判断字符集编码是否为null
		if(charset==null) {
			charset = "UTF-8";
		}
		//2、判断用户是否传递参数
		if(params != null) {
			
//			String str = null;
//			for (Entry<String, String> entry : params.entrySet()) {
//				str = str + entry.getKey() + "=" + entry.getValue() + "&";
//			}
//			str = str.substring(0,str.length()-1);
//			HttpEntity entity = new StringEntity(str,charset);
//			httpPost.setEntity(entity);
			
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : params.entrySet()) {
				NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
				parameters.add(nvp);
			}
			HttpEntity urlEncodedFormEntity = null;
			try {
				urlEncodedFormEntity = new UrlEncodedFormEntity(parameters);
			} catch (UnsupportedEncodingException e) {
				System.out.println("=================================================");
				e.printStackTrace();
				System.out.println("*************************************************");
			}
			httpPost.setEntity(urlEncodedFormEntity);
			
		}
		//3、封装请求对象HttpGet
		httpPost.setConfig(requestConfig);
		//4、发起http请求
		try {
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == 200) {
				//如果返回值的状态信息是200，表明请求正确
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			System.out.println("=================================================");
			e.printStackTrace();
			System.out.println("*************************************************");
			throw new RuntimeException();
		}
		return result;
	}
	
	public String doPost(String url) {
		return doPost(url,null,null);
	}
	
	public String doPost(String url,Map<String,String> params) {
		return doPost(url,params,null);
	}
	
	public String doPost(String url,String charset) {
		return doPost(url,null,charset);
	}
}
