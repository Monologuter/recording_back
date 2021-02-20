package com.ruoyi.gb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ApiRsp;
import com.ruoyi.common.utils.CodeDefine;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.service.IGbMonitorService;
import com.ruoyi.gb.service.IGbOperateService;
import com.ruoyi.gb.service.XmlTemplateService;

import cn.hutool.http.HttpRequest;

@Service
public class GbOperateServiceImpl implements IGbOperateService {
	@Autowired
	private XmlTemplateService xmlTemplateService;
	@Autowired
	private IGbMonitorService gbMonitorService;

	@Override
	public AjaxResult DeviceControl(String deviceID, String type) {
		try {
			GbMonitor getByDeviceId = gbMonitorService.getOne(new QueryWrapper<GbMonitor>().eq("device_id", deviceID));
			if (null == getByDeviceId) {
				return AjaxResult.error("当前控制监控系统中不存在");
			}
			String PTZCmd = "";
			if (type.equals(CodeDefine.MONITOR_LEFT)) {
				PTZCmd = CodeDefine.MONITOR_LEFT_PTZCMD;
			} else if (type.equals(CodeDefine.MONITOR_RIGHT)) {
				PTZCmd = CodeDefine.MONITOR_RIGHT_PTZCMD;
			} else if (type.equals(CodeDefine.MONITOR_DOWN)) {
				PTZCmd = CodeDefine.MONITOR_DOWN_PTZCMD;
			} else if (type.equals(CodeDefine.MONITOR_UP)) {
				PTZCmd = CodeDefine.MONITOR_UP_PTZCMD;
			} else if (type.equals(CodeDefine.MONITOR_IN)) {
				PTZCmd = CodeDefine.MONITOR_IN_PTZCMD;
			} else if (type.equals(CodeDefine.MONITOR_OUT)) {
				PTZCmd = CodeDefine.MONITOR_OUT_PTZCMD;
			} else if (type.equals(CodeDefine.MONITOR_STOP)) {
				PTZCmd = CodeDefine.MONITOR_STOP_PTZCMD;
			} else {
				return AjaxResult.error();
			}

			String xml = xmlTemplateService.DeviceControl(deviceID, PTZCmd);
			String url = xmlTemplateService.getUrl(getByDeviceId.getDomainId());
			HttpRequest hreq = HttpRequest.post(url);
			String result2 = hreq.body(xml).timeout(5000).execute().body();
			if (result2.contains("200")) {
				return AjaxResult.success();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error(e.getMessage());
		}
		return AjaxResult.error();
	}

}
