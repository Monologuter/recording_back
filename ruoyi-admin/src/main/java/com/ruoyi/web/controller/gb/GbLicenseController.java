package com.ruoyi.web.controller.gb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ConfigProperUtil;
import com.ruoyi.common.utils.PropertyUtil;
import com.ruoyi.common.utils.license.LicenseUtil;
import com.ruoyi.common.utils.license.MmCode;
import com.ruoyi.gb.domain.GbConfig;
import com.ruoyi.gb.domain.GbMonitor;
import com.ruoyi.gb.domain.GbSysConfig;
import com.ruoyi.gb.service.IGbConfigService;
import com.ruoyi.gb.service.IGbMonitorService;
import com.ruoyi.gb.service.IGbSysConfigService;




@RequestMapping("gb/license")
@Controller
public class GbLicenseController {
	@Autowired
	private IGbSysConfigService gbSysConfigService;
	@Autowired
	private IGbMonitorService gbMonitorService;
	@Autowired
	private IGbConfigService gbConfigService;
	
	@PostConstruct
	public void loadLicense() {
		try {
			String lincenseAddress = gbSysConfigService.getOne(new QueryWrapper<GbSysConfig>().eq("param_name", "license_address")).getParamValue();
			LicenseUtil license = new LicenseUtil();
			license.reload(lincenseAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/info")
	@ResponseBody
	public AjaxResult addBatch() {
		LicenseUtil license = new LicenseUtil();
		HashMap<String, Object> mmcode = new HashMap<>();
		try {
			mmcode.put("mmcode", MmCode.getCode());
			List<HashMap<String, Object>> data = setLicenseList(license.getLicenseJSONObject());
			data.add(mmcode);
			return AjaxResult.success("查询成功", data);
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("许可获取失败");
		}
	}
	
public List<HashMap<String, Object>> setLicenseList(JSONObject licenseParm) {
	HashMap<String, Object> map = null;
	List<HashMap<String, Object>> data = new ArrayList<>();
	Iterator it = licenseParm.entrySet().iterator();
	while(it.hasNext()) {
		Map.Entry<String, Object> entry = (Entry<String, Object>) it.next();
		switch(entry.getKey()) {
			case "create_time":{
				map = new HashMap<>();
				map.put("type","许可时间");
				map.put("allValue",("").equals(licenseParm.get("expir_time"))?"永久":licenseParm.get("create_time")+"至"+licenseParm.get("expir_time"));
				map.put("usedValue", "");
				map.put("notUsedValue", "");
				map.put("remark", "");
				data.add(map);
			};
			break;
			case "gb28181_num":{
				List<GbConfig> list = gbConfigService.list();
				map = new HashMap<>();
				Integer usedValue = list.size();
				Integer allValue = Integer.parseInt(entry.getValue().toString());
				map.put("type","国标服务器数量");
				map.put("allValue",allValue);
				map.put("usedValue", usedValue);
				map.put("notUsedValue", allValue-usedValue);
				map.put("remark", "最大国标服务器数量");
				data.add(map);
			};
			break;	
			case "monitor_num":{
				List<GbMonitor> list = gbMonitorService.list();
				map = new HashMap<>();
				Integer usedValue = list.size();
				Integer allValue = Integer.parseInt(entry.getValue().toString());
				map.put("type","监控数量");
				map.put("allValue",allValue);
				map.put("usedValue", usedValue);
				map.put("notUsedValue", allValue-usedValue);
				map.put("remark", "系统允许建最大监控的数量");
				data.add(map);
			};
			break;	
			case "historical_video_playback":{
				map = new HashMap<>();
				Integer usedValue = null;
				Integer allValue = Integer.parseInt(entry.getValue().toString());
				String state = allValue==2?"允许":"不允许";
				map.put("type","历史视频回放");
				map.put("allValue",state);
				map.put("usedValue", state);
				map.put("notUsedValue", null);
				map.put("remark", "历史视频回放");
				data.add(map);
			};
			break;
		}
	}
	return data;
}
	

}
