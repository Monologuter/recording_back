package com.ruoyi.gb.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.domain.GbAlarm;

public interface IGbAlarmService extends IService<GbAlarm>{

	int saveGbAlarm(GbAlarm gbAlarm);

	List<GbAlarm> listGbAlarm(GbAlarm gbAlarm);

}
