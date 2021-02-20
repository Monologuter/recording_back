package com.ruoyi.gb.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("gb_alarm")
public class GbAlarm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String deviceId;
	private String alarmPriority;
	private String alarmMethod;
	private String alarmTime;
	private String alarmDescription;
	private String alarmInfo;
	private String alarmType;
	private String eventType;
	private String longitude;
	private String latitude;
	private String domainId;
	private String ipAddress;
	
	@TableField(exist = false)
	private String configName;

	
	
	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public GbAlarm() {
		super();
	}

	public GbAlarm(String id, String deviceId, String alarmPriority, String alarmMethod, String alarmTime,
			String alarmDescription, String alarmInfo, String alarmType, String eventType, String longitude,
			String latitude,String ipAddress,String domainId) {
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.alarmPriority = alarmPriority;
		this.alarmMethod = alarmMethod;
		this.alarmTime = alarmTime;
		this.alarmDescription = alarmDescription;
		this.alarmInfo = alarmInfo;
		this.alarmType = alarmType;
		this.eventType = eventType;
		this.longitude = longitude;
		this.latitude = latitude;
		this.domainId = domainId;
		this.ipAddress = ipAddress;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAlarmPriority() {
		return alarmPriority;
	}

	public void setAlarmPriority(String alarmPriority) {
		this.alarmPriority = alarmPriority;
	}

	public String getAlarmMethod() {
		return alarmMethod;
	}

	public void setAlarmMethod(String alarmMethod) {
		this.alarmMethod = alarmMethod;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAlarmDescription() {
		return alarmDescription;
	}

	public void setAlarmDescription(String alarmDescription) {
		this.alarmDescription = alarmDescription;
	}

	public String getAlarmInfo() {
		return alarmInfo;
	}

	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "id=" + id + ", deviceId=" + deviceId + ", alarmPriority=" + alarmPriority + ", alarmMethod="
				+ alarmMethod + ", alarmTime=" + alarmTime + ", alarmDescription=" + alarmDescription + ", alarmInfo="
				+ alarmInfo + ", alarmType=" + alarmType + ", eventType=" + eventType + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", domainId=" + domainId + ", ipAddress=" + ipAddress + ", configName="
				+ configName;
	}
	
	

}
