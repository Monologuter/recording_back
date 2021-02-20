package com.ruoyi.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ApiRsp;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.GbCodeDefine;
import com.ruoyi.common.utils.RequestBodyUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.domain.GbConfig;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.service.IGbAddressService;
import com.ruoyi.gb.service.IGbConfigService;
import com.ruoyi.gb.service.IGbMonitorService;
import com.ruoyi.gb.service.IGbOperateService;
import com.ruoyi.gb.service.IGbSysConfigService;
import com.ruoyi.gb.service.MonitorLiveAddressService;
import com.ruoyi.gb.service.XmlTemplateService;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;

@RestController
@RequestMapping("/api")
public class ApiController  extends BaseController{
	private final static String SALT = "GB28181_ckinfo";
	private final static int REPART = 3;
	
	@Value("${LocalIP}")
	private String LocalIP;
	@Value("${LocalPort}")
	private String LocalPort;
	@Value("${ServerIP}")
	private String ServerIP;
	@Value("${ServerPort}")
	private String ServerPort;
	@Value("${USERNAME}")
	private String USERNAME;
	@Value("${PASSWD}")
	private String PASSWD;
	@Value("${GB_PATH}")
	private String GB_PATH;
	@Value("${HTTP}")
	private String HTTP;
	@Value("${GB28181PORT}")
	private String GB28181PORT;
	@Value("${GB28181CODE}")
	private String GB28181CODE;
	@Value("${HTTPURL}")
	private String HTTPURL;
	@Value("${CLOSE_WAIT_TIME}")
	private String CLOSE_WAIT_TIME;
	
	@Autowired
	private XmlTemplateService xmlTemplateService;
	
	@Autowired
	private IGbOperateService gbOperateService;
	
	@Autowired
	private MonitorLiveAddressService monitorLiveAddressService;
	
	@Autowired
	private IGbMonitorService gbMonitorService;
	
	@Autowired
	private IGbAddressService gbAddressService;
	
	@Autowired
	private IGbConfigService gbConfigService;
	
	@Autowired
	private IGbSysConfigService gbSysConfigService;
	
	//普通监控的域标识
	static String monitorDeviceId = "b776b0d1-1322-4f33-a3b5-cf08662d7110";
	//视频网关服务器获取配置
	@RequestMapping(value = "config", method = RequestMethod.GET)
	public Object config(HttpServletRequest request,HttpServletResponse rsp) throws Exception {
		try {
			JSONObject fri = new JSONObject();
			fri.put("LocalIP", LocalIP);
			fri.put("LocalPort", LocalPort);
			fri.put("ServerIP", ServerIP);
			fri.put("ServerPort", ServerPort);
			fri.put("USERNAME", USERNAME);
			fri.put("PASSWD", PASSWD);
			fri.put("PATH", GB_PATH);
			fri.put("HTTP", HTTP);
			fri.put("GB28181PORT", GB28181PORT);
			fri.put("GB28181CODE", GB28181CODE);
			fri.put("HTTPURL", HTTPURL);
			fri.put("CLOSE_WAIT_TIME", CLOSE_WAIT_TIME);
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("key", encode(fri.toJSONString()));
			
			Map<String,Object> result = new HashMap<String, Object>();
			result.put("cmd", "rsp_msg");
			result.put("data", params);
			result.put("msgid", null);
			result.put("req_cmd", null);
			result.put("res_code", "0");
			result.put("res_msg", "成功");
			return JSON.toJSONString(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> result = new HashMap<String, Object>();
			result.put("cmd", "rsp_msg");
			result.put("data", "");
			result.put("msgid", null);
			result.put("req_cmd", null);
			result.put("res_code", "-1");
			result.put("res_msg", "失败");
			return JSON.toJSONString(result);
		}
	}
	
	//云台控制
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
		return monitorLiveAddressService.monitorLiveAddress(deviceId,format);
	}
	
	
	@GetMapping("cataLog")
	public AjaxResult  cataLog(String domainId) {
		if (StringUtils.isEmpty(domainId)) {
			return AjaxResult.error("域标识不可为空!");
		}
		
		List<GbAddress> addressList = new ArrayList<GbAddress>();
		List<GbMonitor> monitorList = new ArrayList<GbMonitor>();
		if(domainId.equals(monitorDeviceId)) {
			addressList = gbAddressService.list(new QueryWrapper<GbAddress>().eq("type", GbCodeDefine.IS_NOT_GB));
			monitorList = gbMonitorService.list(new QueryWrapper<GbMonitor>().isNull("domain_id"));
		}else {
		     addressList = gbAddressService.list(new QueryWrapper<GbAddress>().eq("domain_id", domainId));
			 monitorList = gbMonitorService.list(new QueryWrapper<GbMonitor>().eq("domain_id", domainId));
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("org", addressList);
		map.put("monitor", monitorList);
		return AjaxResult.success(map);
	}
	
	//base64加密
	public static String encode(String msg) {
		byte[] date = msg.getBytes();
		for (int i = 0; i < REPART; i++) {
			date = (new String(date) + SALT).getBytes();
			date = Base64.getEncoder().encode(date);
		}
		return new String(date);
	}
	
	//base64解密
	public static String decode(String msg) {
		byte[] date = msg.getBytes();
		for (int i = 0; i < REPART; i++) {
			date = Base64.getDecoder().decode(date);
			date = (new String(date)).replace(SALT, "").getBytes();
		}
		return new String(date);
	}
	
	
	
	//国标服务器配置
    @GetMapping("/gbConfig/list")
    public AjaxResult list(GbConfig gbConfig)
    {
    	
    	gbConfig.setState(GbCodeDefine.IS_OPEN);
        List<GbConfig> list = gbConfigService.selectGbConfigList(gbConfig);
        
        GbConfig config = new GbConfig();
        config.setId(UUID.fastUUID().toString());
        config.setDeviceId(monitorDeviceId);
        config.setName("普通监控");
        config.setState(GbCodeDefine.IS_OPEN);
        list.add(config);
        return AjaxResult.success(list);
    }
    
  //监控历史回放
    @GetMapping("/playback")
    public AjaxResult playback(String deviceId,String startTime,String endTime)
    {
    	if(StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
    		return AjaxResult.error("必填参数不可为空!");
    	}
    	GbMonitor gbMonitor = gbMonitorService.getOne(new QueryWrapper<GbMonitor>().eq("device_id", deviceId));
    	if(gbMonitor==null) {
    		return AjaxResult.error("监控不存在!");
    	}
    	try {
    		String uuid = UUID.fastUUID().toString();
        	String url = "http://" + gbSysConfigService.getUrl();
        	String params = "deviceId="+deviceId+"&startTime="+startTime+"&endTime="+endTime+"&uuid="+uuid;
        	String getUrl = url +"/PlayBack/"+ params ;
        	String getResult = HttpUtil
        			                .createGet(getUrl)
        			                .execute()
        			                .charset("utf-8")
        			                .body();
        	if(getResult.contains("body:200")) {
        		return AjaxResult.success("请求成功",url + "/PlayBack/" + uuid + ".flv");
        	}else {
        		return AjaxResult.error("无法播放");
        	}
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error(e.getMessage());
		}
    	
    }
    
}
