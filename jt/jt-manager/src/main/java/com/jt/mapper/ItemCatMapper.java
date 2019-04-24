package com.jt.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemCat;

public interface ItemCatMapper extends BaseMapper<ItemCat> {
	
	@Select("select name from tb_item_cat where id = #{itemCatId}")
	String findItemCatName(@Param("itemCatId")Long itemCatId);

}
