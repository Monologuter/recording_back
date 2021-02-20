package com.ruoyi.gb.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("gb_sys_config")
public class GbSysConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String paramName;
	private String paramValue;
	private String paramDefault;
	private String paramDesc;
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamDefault() {
		return paramDefault;
	}
	public void setParamDefault(String paramDefault) {
		this.paramDefault = paramDefault;
	}
	public String getParamDesc() {
		return paramDesc;
	}
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	
	
}
