package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.service.ItemService;

@RestController
@RequestMapping("/web/item")
public class WebItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 模拟用户请求
	 * manage.jt.com/web/item/findItemById/123456
	 */
	@RequestMapping("/findItemById/{itemId}")
	public Item findItemById(@PathVariable long itemId) {
		return itemService.findItemById(itemId);
	}
	
	
	
}
