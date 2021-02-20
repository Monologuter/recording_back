package com.ruoyi.common.core.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.ruoyi.common.core.domain.model.LoginUser;

/**
 * 存放token和user关系缓存，用于获取user
 */
public class SystemCache {
	 public static ConcurrentHashMap<String, LoginUser> userMap = new ConcurrentHashMap<>();
}
