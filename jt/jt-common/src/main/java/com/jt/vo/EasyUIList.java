package com.jt.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EasyUIList implements Serializable {
	
	private static final long serialVersionUID = 1606759082597060896L;
	
	private Integer total;//记录总数
	private List<?> rows;//保存商品数据
	
	public EasyUIList() {
	}
	
	public EasyUIList(Integer total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	
}
