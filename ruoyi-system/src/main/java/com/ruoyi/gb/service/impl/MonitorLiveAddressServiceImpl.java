/**
 *
 * @dispatch2020
 * @com.ck.core.service.monitor.gb28181
 * @MonitorLiveAddressService.java
 * @author liubaoping
 * @date 2020年12月10日 下午3:21:20
 * @TODO
 */
package com.ruoyi.gb.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ApiRsp;
import com.ruoyi.common.utils.CodeDefine;
import com.ruoyi.common.utils.GbCodeDefine;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.domain.GbSysConfig;
import com.ruoyi.gb.service.IGbAddressService;
import com.ruoyi.gb.service.IGbConfigService;
import com.ruoyi.gb.service.IGbMonitorService;
import com.ruoyi.gb.service.IGbSysConfigService;
import com.ruoyi.gb.service.MonitorLiveAddressService;

import cn.hutool.http.HttpUtil;

/**
 *
 * @dispatch2020
 * @com.ck.core.service.monitor.gb28181
 * @MonitorLiveAddressService.java
 * @author liubaoping
 * @date 2020年12月10日 下午3:21:20
 * @TODO @
 */
@Service
public class MonitorLiveAddressServiceImpl implements MonitorLiveAddressService  {
	@Autowired
	private IGbConfigService gbConfigService;
	@Autowired
	private IGbMonitorService gbMonitorService;
	@Autowired
	private IGbAddressService gbAddressService;
	@Autowired
	private IGbSysConfigService gbSysConfigService;
	
	@Value("${sharePage}")
	private String sharePageUrl;
	
	//普通监控的直播地址获取
	public AjaxResult monitorLiveAddress(String device_id,String format) {
		GbMonitor gbMonitor = gbMonitorService.getOne(new QueryWrapper<GbMonitor>().eq("device_id",device_id));
		String domainId = gbMonitor.getDomainId();
		try {
			String monitor_camera_ip = gbSysConfigService.getOne(new QueryWrapper<GbSysConfig>().eq("param_name", "monitor_camera_ip")).getParamValue();
			String monitor_camera_port = gbSysConfigService.getOne(new QueryWrapper<GbSysConfig>().eq("param_name", "monitor_camera_port")).getParamValue();
	
			String url = null;
			String SLD = null;
			
			//是否国标服务器 调取不同视屏
			if(StringUtils.isNotEmpty(domainId)) {
				url = monitor_camera_ip + ":" + monitor_camera_port + "/RequestSIPPlay/"+domainId+"/"+device_id;
				SLD = "/GB28181/";
			}else {
				url = monitor_camera_ip + ":" + monitor_camera_port + "/Requestlive/"+device_id;
				SLD = "/live/";
			}
			
			
			String result = HttpUtil.get(url);
			if (result.contains("200")) {
				String monitorLiveAddress = "";
				if ("flv".equals(format)) {
					monitorLiveAddress = "http://" + monitor_camera_ip + SLD + device_id + ".flv";
				} else if ("rtmp".equals(format)) {
					monitorLiveAddress = "rtmp://" + monitor_camera_ip + SLD + device_id;
				} else if ("rtsp".equals(format)) {
					monitorLiveAddress = "rtsp://" + monitor_camera_ip + SLD + device_id;
				} else if ("m3u8".equals(format)) {
					monitorLiveAddress = "http://" + monitor_camera_ip + SLD + device_id + ".m3u8";
				} else {
					return AjaxResult.error("请求格式类型不存在");
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("monitorLiveAddress", monitorLiveAddress);
				return AjaxResult.success(map);
			}
			return AjaxResult.error();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error(e.getMessage());
		}
	}
	
	//普通监控的直播地址获取
	public Object playUrl(String type,String code) {
	try {
		String monitor_camera_ip = gbSysConfigService.getOne(new QueryWrapper<GbSysConfig>().eq("param_name", "monitor_camera_ip")).getParamValue();
		String monitor_camera_port = gbSysConfigService.getOne(new QueryWrapper<GbSysConfig>().eq("param_name", "monitor_camera_port")).getParamValue();
		String SLD = null;
		if(GbCodeDefine.IS_28_GB.equals(type)) {
			SLD = "/GB28181/";
		}else {
			SLD = "/live/";
		}
		
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("flv",  "http://" + monitor_camera_ip  + SLD + code + ".flv");
		data.put("rtmp",  "rtmp://" + monitor_camera_ip + SLD + code);
		data.put("rtsp",  "rtsp://" + monitor_camera_ip  + SLD + code + ".sdp");
		data.put("hls",  "http://" + monitor_camera_ip + ":" + monitor_camera_port + "/" + code + "/" + code+"_live.m3u8");
		data.put("sharePage",  "http://" + sharePageUrl + "?deviceId=" + code);
		return data;
	} catch (Exception e) {
		e.printStackTrace();
		return AjaxResult.error(e.getMessage());
	}
}
	
//	public static void main(String[] args) {
//		String monitor_camera_ip = "192.168.10.172";
//		String monitor_camera_port = "7777";
//		String url = monitor_camera_ip + ":" + monitor_camera_port + "/Requestlive/7000";
//		String result = HttpUtil.get(url);
//		JSONObject jsonObject = JSONObject.parseObject(result);
//	}
}
