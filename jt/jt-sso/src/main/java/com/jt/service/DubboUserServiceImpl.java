package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service
public class DubboUserServiceImpl implements DubboUserService {
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional
	public void saveUser(User user) {
//		String solt = "cn.tedu.tarena";
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
	}

	/**
	 * 步骤：
	 * 	1、根据用户名和密码查询数据库
	 * 	2、生成秘钥token串，MD5加密
	 * 	3、把用户对象转化为json
	 * 	4、将数据保存到redis中
	 */
	@Override
	public String findUserByUP(User user) {
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", user.getUsername()).eq("password", md5DigestAsHex);
		User userDB = userMapper.selectOne(queryWrapper);
		if(userDB == null) {
			return null;
		}
		//程序执行到这里，表示用户名和密码正确
		String token = "JT_TICKEt"+System.currentTimeMillis()+user.getUsername();
		token = DigestUtils.md5DigestAsHex(token.getBytes());
		//必须进行脱敏处理
		userDB.setPassword("你猜猜!");
		String userJson = ObjectMapperUtil.toJson(userDB);
		jedisCluster.setex(token, 7*24*60*60, userJson);
		return token;
	}
	
}
