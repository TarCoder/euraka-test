package com.jt.util;

import com.jt.pojo.User;

/**
 * 该对象保存的是用户信息
 */
public class UserThreadLocal {
	
	/**
	 * JVM直接创建
	 */
	private static ThreadLocal<User> thread = new ThreadLocal<>();
	
	public static void set(User user) {
		thread.set(user);
	}
	
	public static User get() {
		return thread.get();
	}
	
	public static void remove() {
		thread.remove();
	}
}
