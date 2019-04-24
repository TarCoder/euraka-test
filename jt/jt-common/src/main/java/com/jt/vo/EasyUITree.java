package com.jt.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * VO对象最终转化为JSON串,调用get方法,无需序列化
 * 如果当前对象需要远程传输,必须序列化
 */
@Data
@Accessors(chain=true)
public class EasyUITree implements Serializable {
	private static final long serialVersionUID = 8073611123723608017L;
	private Long id;
	private String text;
	private String state;
	public EasyUITree() {
		super();
	}
	public EasyUITree(Long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}
}
