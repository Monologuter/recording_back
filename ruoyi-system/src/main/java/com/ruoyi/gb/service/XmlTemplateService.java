package com.ruoyi.gb.service;

public interface XmlTemplateService {

	String Catalog(String deviceID);

	String getUrl(String deviceID);

	String DeviceControl(String deviceID, String pTZCmd);

	String PresetQuery(String deviceID);
	
}
