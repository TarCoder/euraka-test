package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIList;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	//实现商品的分页查询
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIList findItemByPage(Integer page,Integer rows) {
		return itemService.findItemByPage(page,rows);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.saveItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	@RequestMapping("instock")
	@ResponseBody
	public SysResult instock(Long[] ids) {
		try {
			int status = 2;
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	@RequestMapping("reshelf")
	@ResponseBody
	public SysResult reshelf(Long[] ids) {
		try {
			int status = 1;
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	/**
	 * 实现商品修改操作
	 */
	@RequestMapping("update")
	@ResponseBody
	public SysResult update(Item item,ItemDesc itemDesc) {
		try {
			itemService.updateItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public SysResult delete(Long[] ids) {
		try {
			itemService.deleteItems(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//根据itemId查询商品详情信息
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable("itemId")Long itemId) {
		try {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			return SysResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	@RequestMapping("abc")
	@ResponseBody
	public String abc() {
		itemService.abc();
		return null;
	}
	
}
