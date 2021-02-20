package com.ruoyi.gb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.domain.GbSysConfig;
import com.ruoyi.gb.mapper.GbAddressMapper;
import com.ruoyi.gb.mapper.GbMonitorMapper;
import com.ruoyi.gb.mapper.GbSysConfigMapper;
import com.ruoyi.gb.service.IGbMonitorService;
import com.ruoyi.gb.service.IGbSysConfigService;

@Service
public class GbSysConfigServiceImpl extends ServiceImpl<GbSysConfigMapper, GbSysConfig>implements IGbSysConfigService {
	
	@Autowired
	private GbSysConfigMapper gbSysConfigMapper;
	
	public String getUrl() {
		String port = gbSysConfigMapper.selectOne(new QueryWrapper<GbSysConfig>().eq("param_name", "monitor_camera_port")).getParamValue();
		String ip = gbSysConfigMapper.selectOne(new QueryWrapper<GbSysConfig>().eq("param_name", "monitor_camera_ip")).getParamValue();
		return ip + ":" + port ;
	}
}
