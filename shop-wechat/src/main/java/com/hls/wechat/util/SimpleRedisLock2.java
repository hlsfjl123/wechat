package com.hls.wechat.util;

import cn.hutool.core.util.IdUtil;
import com.hls.wechat.service.Ilock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 判断锁和删除锁存在风险  可以整合为原子性操作
 * lua脚本解决
 *
 * @Author: User-XH251
 * @Date: 2022/8/3 11:38
 */
public class SimpleRedisLock2 implements Ilock {

    private StringRedisTemplate stringRedisTemplate;

    private String name;

    private static final String KEY_PREFIX = "lock:";

    private static final String ID_PREFIX = IdUtil.fastUUID() + "-";
    //ctrl+shift+u  可以让选中的大小写转换
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;

    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }

    public SimpleRedisLock2(StringRedisTemplate stringRedisTemplate, String name) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.name = name;
    }

    @Override
    public boolean tryLock(long timoutSec) {
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name, threadId, timoutSec, TimeUnit.SECONDS);
        //防止空指针的判断
        return Boolean.TRUE.equals(setIfAbsent);
    }

    @Override
    public void unlock() {
        //获取线程标识
//        String threadId = ID_PREFIX + Thread.currentThread().getId();
//        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
//        if (threadId.equals(id)) {
//            stringRedisTemplate.delete(KEY_PREFIX + name);
//        }
        //lua脚本
        stringRedisTemplate.execute(UNLOCK_SCRIPT, Collections.singletonList(KEY_PREFIX + name),ID_PREFIX + Thread.currentThread().getId());

    }
}
