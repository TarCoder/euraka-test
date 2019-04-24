package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Reference(timeout=5000,check=false)
	private DubboUserService userService;
	
//	@Autowired
//	private UserService userService;
	
	/**
	 * 用户登出操作
	 * 1、先获取用户浏览器端的cookie数据
	 * 2、根据token数据删除redis
	 * 3、删除cookie
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		//2、删除redis数据
		jedisCluster.del(token);
		//3、删除cookie  0：立即删除   -1：关闭会话时删除
		Cookie cookie = new Cookie("JT_TICKET", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:index.html";
	}
	
	/**
	 * 返回值要求：
	 * 	如果用户名或密码错误，则token为null
	 * 	如果用户或密码正确，token中有数据
	 * 业务流程：
	 *  1、判断数据是否邮箱
	 *  2、如果有效应该保存到cookie中
	 *  
	 * 关于Cookie的补充知识
	 * 	1、setPath("/")
	 * 		只要是jt项目中的页面都可以访问这个cookie
	 * 	2、setPath("/sso/")
	 * 		只有位于sso/下面的页面才可以访问这个cookie
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		try {
			String token = userService.findUserByUP(user);
			if(!StringUtils.isEmpty(token)) {
				Cookie cookie = new Cookie("JT_TICKET", token);
				cookie.setMaxAge(7*24*60*60);
				cookie.setPath("/");//一般默认写"/"
				response.addCookie(cookie);
				return SysResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.fail();
	}
	
	//实现用户的注册
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
//		SysResult result = null;
		try {
			userService.saveUser(user);
			return SysResult.ok();
//			result = userService.saveUser(user);
//			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//实现页面的通用跳转
	@RequestMapping("/{page}")
	public String page(@PathVariable String page) {
		return page;
	}
	
	
	
}
