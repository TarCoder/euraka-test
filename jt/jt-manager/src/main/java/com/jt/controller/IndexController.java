package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	/**
	 * 思路:能够接收用户请求参数,实现页面动态跳转
	 * RESTFUL结构
	 *  1. 参数必须使用"/"分割,如果有多个参数则写多个"/"
	 *  2. 参数必须使用{}包裹,并且命名参数名称
	 *  3. 必须添加参数@PathVariable
	 * 特点:可以将url中的请求路径信息动态获取数据
	 * 要求:传参时名称需要统一
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable("moduleName") String moduleName) {
		return moduleName;
	}
	
	@RequestMapping("/page/item-list")
	public String module2() {
		return "item-list";
	}
}
