package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.annotation.Cache;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIList;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	@Cache
	public EasyUIList findItemByPage(Integer page, Integer rows) {
		int total = itemMapper.selectCount(null);
		//获取分页后的数据
		int start = (page-1)*rows;
		List<Item> list = itemMapper.findItemListByPage(start,rows);
		return new EasyUIList(total,list);
	}
	
	/**@Transactional 知识回顾
	 * propagation:表示事务传播属性
	 * 		1. REQUIRED:必须添加事务(默认值)
	 * 		2. SUPPORTS:事务支持的.例如:刚执行了save操作,之后执行select操作,会使用同一个事务.
	 * 		   一般只有select操作时才会添加SUPPORTS
	 * 		3. REQUIRES_NEW:会开启一个全新的事务
	 * 		4. NEVER:从不添加事务,做爬虫操作时使用
	 * 异常处理机制:
	 * 		如果是运行时异常,则spring负责事务回滚
	 * 		如果是编译异常(检查异常),spring不负责事务回滚
	 * rollbackFor:异常.class  指定异常类型回滚事务
	 * noRollbackFor:异常.class  指定异常不回滚
	 */
	@Override
	@Transactional
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1);//1表示正常,2表示下架
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	/**
	 * 说明:该操作时一个批量更新操作
	 * sql:update tb_item set status = #{status},updated = #{updated} where id in ()
	 * 实现方法一: 面向对象的方式操作
	 * 实现方法二: 自己写sql
	 */
	@Transactional
	@Override
	public void updateStatus(Long[] ids, Integer status) {
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		List<Long> value = Arrays.asList(ids);
		updateWrapper.in("id", value);
		itemMapper.update(item, updateWrapper);
	}
	
	@Transactional
	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		itemDesc.setItemId(item.getId()).setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}
	
	@Transactional
	@Override
	public void deleteItems(Long[] ids) {
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}

	@Override
	@Cache
	public Integer abc() {
		return null;
	}

	@Override
	public Item findItemById(long itemId) {
		Item item = itemMapper.selectById(itemId);
		return item;
	}
	
	
	
	
	
	
	
}
