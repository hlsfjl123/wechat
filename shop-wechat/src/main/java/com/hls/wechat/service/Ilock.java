package com.hls.wechat.service;

/**
 * 分布式锁雏形
 * @Author: User-XH251
 * @Date: 2022/8/3 11:36
 */
public interface Ilock {

    /**
     * 尝试获取锁
     * @param timoutSec
     * @return
     */
    boolean tryLock(long timoutSec);


    void  unlock();
}
