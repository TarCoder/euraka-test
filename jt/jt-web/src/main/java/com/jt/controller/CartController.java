package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.CartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(timeout=5000,check=false)
	private CartService cartService;
	
	@RequestMapping("/show")
	public String show(Model model,HttpServletRequest request) {
		//获取当前用户信息
		Long userId = UserThreadLocal.get().getId();
		//1、获取购物车信息
		List<Cart> cartList = cartService.findCartList(userId);
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	
	@ResponseBody
	@RequestMapping("/update/num/{itemId}/{num}")
	public SysResult updateNum(Cart cart) {
		try {
			Long userId = UserThreadLocal.get().getId();
			cart.setUserId(userId);
			cartService.updateCartNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	@RequestMapping("/add/{itemId}")
	public String addCart(Cart cart) {
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
}
