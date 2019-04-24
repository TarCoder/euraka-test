package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;

public interface ItemMapper extends BaseMapper<Item>{
	/**
	 * mybatis取值传参问题:不允许多值传参,只能将多值转换成单值
	 * 	1. 把参数封装称POJO对象
	 * 	2. 封装为Map集合
	 * 	3. 封装为array/list集合
	 */

	List<Item> findItemListByPage(@Param("start")Integer start, @Param("rows")Integer rows);
	
}
