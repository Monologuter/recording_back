package com.ruoyi.gb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.gb.domain.GbMonitor;

public interface IGBNoticeService {
	public void notice(String type,String body);
}
