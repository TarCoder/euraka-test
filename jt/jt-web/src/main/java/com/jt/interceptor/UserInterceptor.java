package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

/**
 * 核心：获取用户的访问数据/路径request/response
 *
 */
@Component
public class UserInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * 完成用户校验，如果没有登陆，则跳转用户登陆页面
	 * 如果用户登陆则放行
	 * boolen：true 放行  false：拦截+重定向
	 * 
	 * 用户拦截器实现步骤：
	 * 	1、首先获取用户的cookie数据
	 * 	2、判断用户是否已经登陆
	 * 		如果用户没有登陆则重定向到用户登陆页面
	 * 		如果用户已经登陆，则判断redis中是否有数据
	 * 			有：表示用户之前登陆成功，予以放行
	 * 			无：表示用户登陆失败，之后重定向到登陆页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		if(!StringUtils.isEmpty(token)) {
			//判断redis中是否有数据
			String userJSON = jedisCluster.get(token);
			if(!StringUtils.isEmpty(userJSON)) {
				//程序执行到这里表示用户已经登陆
				User user = ObjectMapperUtil.toObject(userJSON, User.class);
				//将user信息存到线程中
				UserThreadLocal.set(user);
				return true;
			}
		}
		//如果程序执行到这里，表示用户一定没有登陆
		response.sendRedirect("/user/login.html");
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//删除线程数据
		UserThreadLocal.remove();
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
