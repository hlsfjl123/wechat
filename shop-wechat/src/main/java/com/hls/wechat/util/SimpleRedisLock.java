package com.hls.wechat.util;

import cn.hutool.core.util.IdUtil;
import com.hls.wechat.service.Ilock;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 简单版
 * 存在问题  业务阻塞时间太长 超过了ex时间 就会提前释放
 * 比如线程1获取锁  阻塞超过了时间 线程2进来  这时线程1苏醒  把线程2锁删除   线程3就可以进来
 * 解决办法就是查看线程id是否一致
 *
 * @Author: User-XH251
 * @Date: 2022/8/3 11:38
 */
public class SimpleRedisLock implements Ilock {

    private StringRedisTemplate stringRedisTemplate;

    private String name;

    private static final String KEY_PREFIX = "lock:";

    private static final String ID_PREFIX = IdUtil.fastUUID() + "-";

    public SimpleRedisLock(StringRedisTemplate stringRedisTemplate, String name) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.name = name;
    }

    @Override
    public boolean tryLock(long timoutSec) {
        //获取标识
//        long threadId = Thread.currentThread().getId();
//        //就是利用setnx ex的互斥效果实现分布式锁
//        Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name, threadId + "", timoutSec, TimeUnit.SECONDS);
        //增加uuid防止集群模式下线程重复
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name, threadId, timoutSec, TimeUnit.SECONDS);
        //防止空指针的判断
        return Boolean.TRUE.equals(setIfAbsent);
    }

    @Override
    public void unlock() {
        //获取线程标识
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
        if (threadId.equals(id)) {
            stringRedisTemplate.delete(KEY_PREFIX + name);
        }
    }
}
