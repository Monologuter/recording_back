package com.ruoyi.gb.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.gb.domain.GbSysConfig;

public interface GbSysConfigMapper extends BaseMapper<GbSysConfig>{
	
	public GbSysConfig getGbSysOneByParamName(String name);

}
