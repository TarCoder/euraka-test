package com.jt.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_item_desc")
@Data
@Accessors(chain=true)
public class ItemDesc extends BasePojo {
	private static final long serialVersionUID = 3810185467553974909L;
	@TableId()
	private Long itemId;
	private String itemDesc;
	private Date created;
	private Date updated;
}
