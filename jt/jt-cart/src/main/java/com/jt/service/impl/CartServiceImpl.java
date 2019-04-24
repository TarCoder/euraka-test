package com.jt.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.service.CartService;
import com.jt.service.ItemService;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import com.jt.pojo.Item;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartMapper cartMapper;
	
	@Reference(timeout=5000,check=false)
	private ItemService itemService;

	@Override
	public List<Cart> findCartList(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		List<Cart> list = cartMapper.selectList(queryWrapper);
		return list;
	}

	/**
	 * 修改商品的数量
	 * entity：修改的值
	 * updateWrapper：where条件
	 */
	@Override
	public void updateCartNum(Cart cart) {
		Cart entity = new Cart();
		Item item = itemService.findItemById(cart.getItemId());
		entity.setNum(cart.getNum()).setUpdated(new Date());
		entity.setItemPrice(item.getPrice()*entity.getNum());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
		cartMapper.update(entity, updateWrapper);
	}

	/**
	 * 1、如果当前商品数据库中已经存在，则数量update
	 * 2、如果当前商品数据库中没有，则insert
	 */
	@Override
	@Transactional
	public void saveCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		queryWrapper.eq("item_id", cart.getItemId()).eq("user_id", cart.getUserId());
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		if(cartDB == null) {//表示用户第一次购买
			cart.setCreated(new Date()).setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {//表示用户已经购买过了
			int num = cart.getNum() + cartDB.getNum();
			cartDB.setNum(num).setUpdated(new Date());
			Item item = itemService.findItemById(cart.getItemId());
			cartDB.setItemPrice(item.getPrice()*cartDB.getNum());
			cartMapper.updateById(cartDB);
		}
	}
	

}
