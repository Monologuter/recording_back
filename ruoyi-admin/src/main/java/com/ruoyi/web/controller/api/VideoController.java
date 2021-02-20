package com.ruoyi.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.gb.service.MonitorLiveAddressService;

@RestController
@RequestMapping("/video")
public class VideoController extends BaseController{
	
	@Autowired
	private MonitorLiveAddressService monitorLiveAddressService;
	
	@GetMapping("playUrl")
	public AjaxResult  playUrl(String type,String code) {
		if (StringUtils.isEmpty(type) || StringUtils.isEmpty(code)) {
			return AjaxResult.error("必填参数不可为空!");
		}
		
		Object data = monitorLiveAddressService.playUrl(type,code);
		return AjaxResult.success(data);
//		Map<String, Object> res = new HashMap<String, Object>();
//		res.put("code", 200);
//		res.put("data", data);
	}
}
