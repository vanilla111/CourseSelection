package com.database.cs.service.impl;

import com.database.cs.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisLockServiceImpl {

    private boolean isLock = false;

    private String key;

    @Autowired
    private StringRedisTemplate redis;

    public void lock(String key, String value) {
        this.key = key;
        long nowTime = System.nanoTime();
        long outTime = Constant.LOCK_TIME_OUT * 1000000;
        while ((System.nanoTime() - nowTime) < outTime) {
            if (redis.opsForValue().setIfAbsent(key, value)) {
                isLock = true;
                redis.expire(key, Constant.EXPIRE_TIME, TimeUnit.SECONDS);
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                //
            }
        }
    }

    public void unlock() {
        if (isLock) {
            redis.delete(key);
            isLock = false;
        }
    }

    public boolean isLock() {
        return isLock;
    }
}
