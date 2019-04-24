package com.jt.config;

import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jt.interceptor.UserInterceptor;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer{
	
	@Autowired
	private UserInterceptor interceptor;
	
	//开启匹配后缀型配置
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		configurer.setUseSuffixPatternMatch(true);
	}
	
	/**
	 * 	由于伪静态实现servlet配置.
	 * 	添加2个请求路径
	 * 		1.拦截后缀型请求   *.html
	 * 		2.添加拦截前缀/service/*
	 * 	以下配置相当于修改了web.xml中的servlet配置
	 * 	
	 * @param dispatcherServlet
	 * @return
	 */
	@Bean
	public ServletRegistrationBean<Servlet> servletConfig(DispatcherServlet dispatcherServlet){
		ServletRegistrationBean<Servlet> servletBean = 
				new ServletRegistrationBean<>(dispatcherServlet);
		servletBean.getUrlMappings().clear();				//清空之前的默认配置
		servletBean.addUrlMappings("/service/*","*.html");	//添加2个拦截路径
		return servletBean;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor).addPathPatterns("/cart/**","/order/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
