package com.jt.service;

import java.util.List;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

public interface ItemCatService {

	ItemCat queryItemName(Long itemCatId);

	List findItemCatList(Long parentId);
	
	List findItemCatCacheList(Long parentId);

}
