package com.ruoyi.gb.service;

import org.dom4j.Document;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.ApiRsp;
import com.ruoyi.gb.domain.GbAddress;

public interface ICatalogService {

	void Catalog(Document doc, String parentDeviceID);
	
	String update_gb28181_Catalog(String deviceID);
	
	void Keepalive(Document doc, String parentDeviceID);

	void saveAlarm(Document doc, String parentDeviceID);

}
