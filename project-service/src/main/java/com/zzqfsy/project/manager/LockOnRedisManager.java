package com.zzqfsy.project.manager;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 12:27 2018/8/20
 * @Modified By:
 **/
@Component
public class LockOnRedisManager {
    private static final Logger logger = LoggerFactory.getLogger(LockOnRedisManager.class);

    @Autowired
    private RedissonClient redissonClient;

    public RLock getLock(String key){
        return getLock(key, false);
    }

    public RLock getLock(String key, Boolean isFair){
        return isFair ? redissonClient.getFairLock(key) : redissonClient.getLock(key);
    }

    public RReadWriteLock getReadWriteLock(String key){
        return redissonClient.getReadWriteLock(key);
    }

    public Object handle(Boolean isFair, String key, long waitTime, long leaseTime, Callable callable){
        Object result = null;
        RLock lock = this.getLock(key, isFair);
        boolean isGainLock = false;
        try {
            if (!lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)) {
                return null;
            }
            isGainLock = true;
            result = callable.call();
        } catch (Exception e) {
//            logger.error(String.format("LockOnRedisManger %s happen exception", callable.getClass().toString()), e);
            throw new RuntimeException(e);
        } finally {
            if (isGainLock) lock.unlock();
        }

        return result;
    }

    public <T> T handleByUnfair(String key, long waitTime, long leaseTime,  Class<T> clazz, Callable callable){
        Object object = handle(false, key, waitTime, leaseTime, callable);
        if (object == null) return null;
        return (clazz.getName().equals(object.getClass().getName()) ? (T) object : null);
    }

    public <T> T handleByFair(String key, long waitTime, long leaseTime,  Class<T> clazz, Callable callable){
        Object object = handle(true, key, waitTime, leaseTime, callable);
        if (object == null) return null;
        return (clazz.getName().equals(object.getClass().getName()) ? (T) object : null);
    }
}
