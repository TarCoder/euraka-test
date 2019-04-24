package com.jt.quartz;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;

//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{
	
	@Autowired
	private OrderMapper orderMapper;
	
	/**
	 * 当定时任务执行时，执行job具体操作
	 */
	@SuppressWarnings("static-access")
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//获取格林威治时间
		Calendar calendar = Calendar.getInstance();
		System.out.println("**"+calendar+"**");
		calendar.add(calendar.MINUTE, -15);//进行时间的运算
		Date val = calendar.getTime();
		Order entity = new Order();
		entity.setStatus(6).setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("status", 1).lt("created", val);
		orderMapper.update(entity, updateWrapper);
		System.out.println("定时任务执行成功");
	}
}
