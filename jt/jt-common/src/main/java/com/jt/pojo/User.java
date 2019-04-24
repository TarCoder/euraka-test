package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("tb_user")
public class User extends BasePojo {
	
	private static final long serialVersionUID = -2015513995087701772L;
	
	@TableId(type=IdType.AUTO)	//主键自增
	private Long id;			//用户id
	private String username;	//用户名
	private String password;	//密码
	private String phone;		//电话
	private String email;		//邮箱
	
}
