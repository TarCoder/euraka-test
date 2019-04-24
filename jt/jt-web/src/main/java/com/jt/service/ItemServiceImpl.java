package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.util.HttpClientUtil;
import com.jt.util.ObjectMapperUtil;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private HttpClientUtil httpClientService;
	@Override
	public Item findItemById(Long itemid) {
		String url = "http://manage.jt.com/web/item/findItemById/" + itemid;
		String result = httpClientService.doGet(url);
		Item item = ObjectMapperUtil.toObject(result, Item.class);
		return item;
	}
	
}
