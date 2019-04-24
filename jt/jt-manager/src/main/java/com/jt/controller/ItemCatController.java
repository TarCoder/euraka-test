package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.mapper.ItemCatMapper;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 关于框架编码的说明:
	 * 	1. 使用旧的SSM框架时,如果返回数据为String,则将数据通过@RespongseBody返回时,
	 * 	采用ISO-8859-1编码格式,所以返回数据必定乱码
	 * 	解决方法: @RequestMapping(value="queryItemName",produces="text/html;charset=utf-8")
	 * 	2. 如果返回数据为对象时(pojo/vo),采用UTF-8格式编码
	 * 	3. 当SpringBoot时,返回的数据都是UTF-8
	 */
	@RequestMapping("queryItemName")
	@ResponseBody
	public String queryItemName(HttpServletResponse response,Long itemCatId) {
//		response.setContentType("text/html;charset=utf-8");
		return itemCatMapper.findItemCatName(itemCatId);
	}
	
	/**
	 * @RequestParam:
	 * 		defaultValue: 如果没有传递参数,则设定默认值
	 * 		name/value: 参数名称
	 * 		required: true/false 是否必须传递参数
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITree> findItemCatList(@RequestParam(name="id",defaultValue="0")Long parentId){
		return itemCatService.findItemCatList(parentId);
	}
	
	
}
