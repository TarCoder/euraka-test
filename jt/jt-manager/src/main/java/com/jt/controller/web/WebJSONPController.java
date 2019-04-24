package com.jt.controller.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.Item;

@RestController
public class WebJSONPController {
	
	
	//获取数据之后转化为json串
	@RequestMapping("/web/testJSONP")
	public JSONPObject getJSONP(String callback) {
		Item item = new Item();
		item.setId(1000L).setTitle("json跨域成功！");
		Map<String,String> map = new HashMap<>();
		map.put("id", "abc");
		map.put("name", "bcd");
		System.out.println("**"+map);
		return new JSONPObject(callback, map);
	}
}
