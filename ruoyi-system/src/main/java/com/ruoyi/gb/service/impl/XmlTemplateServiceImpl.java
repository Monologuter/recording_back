/**
 *
 * @dispatch2020
 * @com.ck.core.service.gb28181
 * @XmlTemplateService.java
 * @author liubaoping
 * @date 2020年11月16日 下午6:49:59
 * @TODO
 */
package com.ruoyi.gb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.gb.domain.GbSysConfig;
import com.ruoyi.gb.mapper.GbSysConfigMapper;
import com.ruoyi.gb.service.XmlTemplateService;

/**
 *
 * @dispatch2020
 * @com.ck.core.service.gb28181
 * @XmlTemplateService.java
 * @author liubaoping
 * @date 2020年11月16日 下午6:49:59
 * @TODO @
 */
@Service
public class XmlTemplateServiceImpl implements XmlTemplateService{
	@Autowired
	private GbSysConfigMapper gbSysConfigMapper;
	
	public String Catalog(String deviceID) {
		String param = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		param += "<Query>";
		param += "<CmdType>Catalog</CmdType>";
		param += "<SN>11974</SN>";
		param += "<DeviceID>" + deviceID + "</DeviceID>";
		param += "</Query>";
		return param;
	}

	public String DeviceControl(String deviceID, String PTZCmd) {
		String param = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		param += "<Control>";
		param += "<CmdType>DeviceControl</CmdType>";
		param += "<SN>11974</SN>";
		param += "<DeviceID>" + deviceID + "</DeviceID>";
		param += "<PTZCmd>" + PTZCmd + "</PTZCmd>";
		param += "</Control>";
		return param;
	}

	public String PresetQuery(String deviceID) {
		String param = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		param += "<Query>";
		param += "<CmdType>PresetQuery</CmdType>";
		param += "<SN>11974</SN>";
		param += "<DeviceID>" + deviceID + "</DeviceID>";
		param += "</Query>";
		return param;
	}

	public String getUrl(String deviceID) {
		String port = gbSysConfigMapper.selectOne(new QueryWrapper<GbSysConfig>().eq("param_name", "monitor_camera_port")).getParamValue();
		String ip = gbSysConfigMapper.selectOne(new QueryWrapper<GbSysConfig>().eq("param_name", "monitor_camera_ip")).getParamValue();
//		String port = gbSysConfigMapper.getGbSysOneByParamName("monitor_camera_port").getParamValue();
//		String ip = gbSysConfigMapper.getGbSysOneByParamName("monitor_camera_ip").getParamValue();
		return ip + ":" + port + "/GB28181Ctrl/" + deviceID;
	}

}
