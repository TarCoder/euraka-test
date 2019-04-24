package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	
	@RequestMapping("getMsg")
	@ResponseBody
	public String getMsg() {
		return "我是8091服务器!";
	}
}
