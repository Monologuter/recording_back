package com.ruoyi.web.controller.gb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ApiRsp;
import com.ruoyi.common.utils.CodeDefine;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.gb.service.IGbOperateService;
import com.ruoyi.gb.service.MonitorLiveAddressService;
import com.ruoyi.gb.service.XmlTemplateService;

import cn.hutool.http.HttpRequest;

@RestController()
@RequestMapping("gb/operate")
public class GbOperateController {
	@Autowired
	private XmlTemplateService xmlTemplateService;
	
	@Autowired
	private IGbOperateService gbOperateService;
	
	@Autowired
	private MonitorLiveAddressService monitorLiveAddressService;
	
	/**
	 *云台控制
	 * @project_name dispatch2020
	 * @package_name com.ck.core.service.monitor.gb28181
	 * @file_name CatalogService.java
	 * @enclosing_method DeviceControl
	 * @return_type ApiRsp
	 * @author liubaoping
	 * @date 2020年11月27日 下午4:16:52
	 * @TODO
	 * @@param jsonobj
	 * @@return
	 */
	@GetMapping("screenOperate")
	public AjaxResult DeviceControl(String deviceId,String type) {
		if (StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(type)) {
			return AjaxResult.error("必填参数不可为空!");
		}
		return gbOperateService.DeviceControl(deviceId,type);
	}
	
	//获取直播地址
	@GetMapping("loadCamera")
	public AjaxResult  monitorLiveAddress(String deviceId,String domainId,String format) {
		if (StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(format)) {
			return AjaxResult.error();
		}
		return monitorLiveAddressService.monitorLiveAddress(deviceId, format);
	}
}
