package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;

@Service
public class ItemDescServiceImpl implements ItemDescService {
	
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		ItemDesc itemDesc = itemDescMapper.selectById(itemId);
		return itemDesc;
	}

}
