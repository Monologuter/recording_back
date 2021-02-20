package com.ruoyi.gb.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * GB28181服务器对象 gb_config
 * 
 * @author ruoyi
 * @date 2020-12-18
 */
@TableName("gb_config")
public class GbConfig 
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** GB28181名称 */
    @Excel(name = "GB28181名称")
    private String name;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    /** 域标识 */
    @Excel(name = "域标识")
    private String deviceId;
    
    /** 创建时间 */
    @Excel(name = "创建时间")
    private Long createTime;
    
    private String type;
    
    private Long updateTime;
    
    private String connectType;
    
    public String getConnectType() {
		return connectType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Long getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}


	public void setConnectType(String connectType) {
		this.connectType = connectType;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
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
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setDeviceId(String deviceId) 
    {
        this.deviceId = deviceId;
    }

    public String getDeviceId() 
    {
        return deviceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("createTime", getCreateTime())
            .append("state", getState())
            .append("deviceId", getDeviceId())
            .toString();
    }
}
