package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	private UserService userService;

	/**
	 * 前台通过jsonp形式实现跨域请求，后台需要特殊个数封装数据
	 * http://sso.jt.com/user/check/{param}/{type}
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject findCheckUser(@PathVariable String param,@PathVariable Integer type,String callback) {
		//返回true表示用户已经存在；返回false表示用户不存在，可以使用
		Boolean flag = userService.findCheckUser(param,type);
		return new JSONPObject(callback,SysResult.ok(flag));
	}

	//实现用户的注册
	@RequestMapping("/doRegister")
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		return SysResult.ok();
	}
	
	/**
	 * 根据token数据获取用户信息
	 */
	@RequestMapping("/query/{token}")
	public JSONPObject findUserByToken(@PathVariable String token,String callback) {
		String userJson = jedisCluster.get(token);
		return new JSONPObject(callback, SysResult.ok(userJson));
	}
	
}
