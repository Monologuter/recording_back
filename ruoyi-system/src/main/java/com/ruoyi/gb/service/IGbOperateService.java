package com.ruoyi.gb.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ApiRsp;

public interface IGbOperateService {

	AjaxResult DeviceControl(String deviceID, String type);

}
