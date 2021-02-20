package com.ruoyi.gb.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 监控对象 gb_monitor
 * 
 * @author ruoyi
 * @date 2020-12-24
 */
@TableName("gb_monitor")
public class GbMonitor implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private String id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String name;

    /** 设备ID */
    @Excel(name = "设备ID")
    private String deviceId;

    /** 设备号 */
    @Excel(name = "设备号")
    private String cameraNo;

    /** 摄像头地址 */
    @Excel(name = "摄像头地址")
    private String cameraUrl;

    /** 监控帐号 */
    @Excel(name = "监控帐号")
    private String cameraAccount;

    /** 监控密码 */
    @Excel(name = "监控密码")
    private String cameraPasswd;
    
    /** 监控密码 */
    @Excel(name = "创建时间")
    private String createtime;
    
    /** 监控心跳，海康监控不用发心跳；0：不发心跳，1：发心跳 */
    @Excel(name = "监控心跳，海康监控不用发心跳；0：不发心跳，1：发心跳")
    private String cameraHead;
    
    /** 通讯录ID */
    private String addressId;
    
    private String domainId;
    
    private String longitude;
    
    private String latitude;
    
    private String ipAddress;
    
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDeviceId(String deviceId) 
    {
        this.deviceId = deviceId;
    }

    public String getDeviceId() 
    {
        return deviceId;
    }
    public void setCameraNo(String cameraNo) 
    {
        this.cameraNo = cameraNo;
    }

    public String getCameraNo() 
    {
        return cameraNo;
    }
    public void setCameraUrl(String cameraUrl) 
    {
        this.cameraUrl = cameraUrl;
    }

    public String getCameraUrl() 
    {
        return cameraUrl;
    }
    public void setCameraAccount(String cameraAccount) 
    {
        this.cameraAccount = cameraAccount;
    }

    public String getCameraAccount() 
    {
        return cameraAccount;
    }
    public void setCameraPasswd(String cameraPasswd) 
    {
        this.cameraPasswd = cameraPasswd;
    }

    public String getCameraPasswd() 
    {
        return cameraPasswd;
    }
    public void setCameraHead(String cameraHead) 
    {
        this.cameraHead = cameraHead;
    }

    public String getCameraHead() 
    {
        return cameraHead;
    }

}
