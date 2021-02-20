package com.ruoyi.gb.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ApiRsp;
import com.ruoyi.gb.domain.GbConfig;

public interface MonitorLiveAddressService {
	
	public AjaxResult monitorLiveAddress(String phone,String format);

	public Object playUrl(String type, String code);
}
