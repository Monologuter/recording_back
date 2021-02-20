package com.ruoyi.common.core.cache;

public class CacheObj {
	/**
     * 缓存对象
     */
    private Object CacheValue;
    /**
     * 缓存过期时间
     */
    private Long ttlTime;
 
    public CacheObj(Object cacheValue, Long ttlTime) {
        CacheValue = cacheValue;
        this.ttlTime = ttlTime;
    }
 
    public Object getCacheValue() {
        return CacheValue;
    }
 
    public Long getTtlTime() {
        return ttlTime;
    }
 
    @Override
    public String toString() {
        return "CacheObj {" +
                "CacheValue = " + CacheValue +
                ", ttlTime = " + ttlTime +
                '}';
    }

}
