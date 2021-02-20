package com.ruoyi.common.core.cache;

import com.ruoyi.common.utils.ConcurrentHashMapCacheUtils;

public class CleanTimeOutThread implements Runnable{
	@Override
    public void run() {
        ConcurrentHashMapCacheUtils.setCleanThreadRun();
        while (true) {
            System.out.println("clean thread run ");
            ConcurrentHashMapCacheUtils.deleteTimeOut();
            try {
                Thread.sleep(ConcurrentHashMapCacheUtils.TEN_MINUTE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
