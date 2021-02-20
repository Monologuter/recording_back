package com.ruoyi.web.controller.gb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.gb.domain.GbAlarm;
import com.ruoyi.gb.domain.GbConfig;
import com.ruoyi.gb.service.IGbAlarmService;

import cn.hutool.core.lang.UUID;

@RestController
@RequestMapping("gb/alarm")
public class GbAlarmController extends BaseController{

	@Autowired
	private IGbAlarmService gbAlarmService; 
	
	@GetMapping("/list")
	public TableDataInfo list(GbAlarm gbAlarm) {
		startPage();
//		QueryWrapper<GbAlarm> query = new QueryWrapper<GbAlarm>();
//		if(StringUtils.isNotEmpty(gbAlarm.getDeviceId())) {
//			query.like("device_id", gbAlarm.getDeviceId());
//		}
//		if(StringUtils.isNotEmpty(gbAlarm.getAlarmPriority())) {
//			query.like("alarm_priority", gbAlarm.getAlarmPriority());
//		}
//		if(StringUtils.isNotEmpty(gbAlarm.getAlarmMethod())) {
//			query.like("alarm_method", gbAlarm.getAlarmMethod());
//		}
//		if(StringUtils.isNotEmpty(gbAlarm.getEventType())) {
//			query.like("event_type", gbAlarm.getEventType());
//		}
//		if(StringUtils.isNotEmpty(gbAlarm.getAlarmDescription())) {
//			query.like("alarm_description", gbAlarm.getAlarmDescription());
//		}
		
		List<GbAlarm> list = gbAlarmService.listGbAlarm(gbAlarm);
		return getDataTable(list);
	}
	
	@PostMapping("/save")
    public AjaxResult add(@RequestBody GbAlarm gbAlarm)
    {
		gbAlarm.setId(UUID.randomUUID().toString());
		return toAjax(gbAlarmService.saveGbAlarm(gbAlarm));
    }
	
}
