package com.ruoyi.gb.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.gb.domain.GbAlarm;
import com.ruoyi.gb.mapper.GbAlarmMapper;
import com.ruoyi.gb.service.IGbAlarmService;

import cn.hutool.core.lang.UUID;

@Service
public class GbAlarmServiceImpl extends ServiceImpl<GbAlarmMapper, GbAlarm> implements IGbAlarmService{

	@Autowired
	private GbAlarmMapper gbAlarmMapper;
	
	@Override
	public int saveGbAlarm(GbAlarm gbAlarm) {
		return gbAlarmMapper.insert(gbAlarm);
	}

	@Override
	public List<GbAlarm> listGbAlarm(GbAlarm gbAlarm) {
		return gbAlarmMapper.listGbAlarm(gbAlarm);
	}

}
