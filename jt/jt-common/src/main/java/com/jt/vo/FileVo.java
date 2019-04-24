package com.jt.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileVo implements Serializable {
	private static final long serialVersionUID = 8126901047190136469L;
	private Integer error = 0;
	private String url;
	private Integer width;
	private Integer height;
	public FileVo() {
		super();
	}
	public FileVo(Integer error, String url, Integer width, Integer height) {
		super();
		this.error = error;
		this.url = url;
		this.width = width;
		this.height = height;
	}
	
}
