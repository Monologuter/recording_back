package com.ruoyi.gb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.domain.GbSysConfig;

public interface IGbSysConfigService extends IService<GbSysConfig>{

	public String getUrl();
}
