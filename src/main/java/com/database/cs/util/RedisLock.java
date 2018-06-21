package com.database.cs.util;

import com.database.cs.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisLock {

    private boolean isLock;

    private String key;

    public RedisLock(String key) {
        this.key = key;
    }

    @Autowired
    private StringRedisTemplate redis;

    public void lock(String value) {
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
        if (isLock) redis.delete(key);
    }

    public boolean isLock() {
        return isLock;
    }
}
