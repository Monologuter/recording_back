package com.ruoyi.gb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.gb.domain.GbAlarm;

@Mapper
public interface GbAlarmMapper extends BaseMapper<GbAlarm>{

	List<GbAlarm> listGbAlarm(GbAlarm gbAlarm);

}
