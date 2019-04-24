package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.annotation.Cache;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Autowired
	private Jedis jedis;

	@Override
	@Cache
	public ItemCat queryItemName(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat;
	}

	@Override
	@Cache
	public List findItemCatList(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id",parentId);
		List<ItemCat> list = itemCatMapper.selectList(queryWrapper);
		//需要返回VOList集合信息,则遍历list集合
		List<EasyUITree> treeList = new ArrayList<>();
		for (ItemCat itemCat : list) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCat.getId()).setText(itemCat.getName()).setState(itemCat.getIsParent()==1 ? "closed" : "open");
			treeList.add(easyUITree);
		}
		return treeList;
	}
	
	/**
	 * 1. 首先应该查询Redis
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List findItemCatCacheList(Long parentId) {
		List<EasyUITree> list = new ArrayList<EasyUITree>();
		//key的定义: 类名_变量
		String key = "ITEM_CAT_" + parentId;
		String result = jedis.get(key);
		if(StringUtils.isEmpty(result)) {
			//缓存中没有数据,查真实数据库信息
			list = findItemCatList(parentId);
			//将数据保存到缓存中
			String json = ObjectMapperUtil.toJson(list);
			jedis.set(key, json);
		}else {
			list = ObjectMapperUtil.toObject(result, list.getClass());
		}
		return list;
	}

}
