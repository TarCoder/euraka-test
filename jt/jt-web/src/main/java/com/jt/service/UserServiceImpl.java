package com.jt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.User;
import com.jt.util.HttpClientUtil;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpClientUtil httpClientCutil;
	
	@Override
	public SysResult saveUser(User user) {
		Map<String,String> map = new HashMap<>();
		map.put("username", user.getUsername());
		map.put("email", user.getEmail());
		map.put("password", user.getPassword());
		map.put("phone", user.getPhone());
		String url = "http://sso.jt.com/user/doRegister";
		String result = httpClientCutil.doPost(url, map);
		SysResult object = ObjectMapperUtil.toObject(result, SysResult.class);
		System.out.println(object);
		return object;
	}
	
}
