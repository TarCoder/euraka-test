package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientUtil;
import com.jt.util.ObjectMapperUtil;

@Service
public class ItemDescServiceImpl implements ItemDescService {
	
	@Autowired
	private HttpClientUtil httpClientService;
	
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/itemdesc/findItemDescById/"+itemId;
		String result = httpClientService.doGet(url);
		ItemDesc itemDesc = ObjectMapperUtil.toObject(result, ItemDesc.class);
		return itemDesc;
	}

}
