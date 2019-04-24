package com.jt.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jt.annotation.Cache;
import com.jt.service.RedisService;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Aspect
@Component
public class CacheAspect {
	
	
	@Autowired
	private JedisCluster jedis;
	
	//实现redis哨兵高可用
	@Autowired
	private RedisService redisService;
	
	@Around("@annotation(cache)")
	public Object findItemCatList(ProceedingJoinPoint jp,Cache cache) throws Throwable{
		Object result = null;
		MethodSignature signature = (MethodSignature)jp.getSignature();
		//获取类
		Class<?> target = jp.getTarget().getClass();
		//获取参数列表
		Object[] args = jp.getArgs();
		//获取方法
		Method method = signature.getMethod();
		//获取方法返回值类型
		Class<?> cls = signature.getReturnType();
		//获取key值(全类名_方法名_参数值)
		String key = target.getName()+"_"+method.getName();
		for (int i = 0;i<args.length;i++) {
			key = key +"_"+ args[i];
		}
		//从Redis里获取数据
		String json = jedis.get(key);
		if(StringUtils.isEmpty(json)) {
			//如果没有,就从数据库中获取数据,并存到Redis里
			result = jp.proceed();
			String json2 = ObjectMapperUtil.toJson(result);
			jedis.set(key, json2);
			//设置缓存的有效时间为600s
//			jedis.expire(key, 600);
			System.out.println("**从数据库获取数据**");
		}else {
			result = ObjectMapperUtil.toObject(json, cls);
			System.out.println("**从Redis获取数据**");
		}
		getRealIp();
		return result;
	}
	
	/**
	 * 获取真实IP
	 * @param request 请求体
	 * @return 真实IP
	 */
//	@Before("@annotation(com.jt.annotation.Cache)")
	public void getRealIp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// 这个一般是Nginx反向代理设置的参数
	    String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
	    System.out.println("*****"+ip+"*****");
	}

	
	
	
}
