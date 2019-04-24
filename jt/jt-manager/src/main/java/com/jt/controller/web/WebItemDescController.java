package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.ItemDesc;
import com.jt.service.ItemDescService;

@RestController
@RequestMapping("/web/itemdesc")
public class WebItemDescController {
	
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping("findItemDescById/{itemId}")
	public ItemDesc findItemDescById(@PathVariable Long itemId) {
		System.out.println("WebItemDescController.findItemDescById()");
		ItemDesc itemDesc = itemDescService.findItemDescById(itemId);
		return itemDesc;
	}
	
}
