package com.jt.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloAspect {
	/**
	 * 切入点表达式的写法:
	 * 1.within("包名.类名") 粗粒度
	 * 		匹配的类中的全部方法都会执行通知方法
	 * 2.excoution("返回值类型 包名.类名(参数列表)")
	 */
	@Before("within(com.jt.controller.HelloController)")
	public void before(JoinPoint jp) {
		System.out.println("HelloAspect.before()");
		String name = jp.getSignature().getName();
		System.out.println("获取目标方法的名称:"+name);
		Class<?> cls = jp.getTarget().getClass();
		System.out.println("获取目标方法类型:"+cls);
	}
	
	@Around("within(com.jt.controller.HelloController)")
	public Object around(ProceedingJoinPoint jp) {
		Object result = null;
		try {
			System.out.println("*HelloAspect.around()");
			result = jp.proceed();
			System.out.println("**HelloAspect.around()");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
