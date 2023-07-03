package com.snow.dcl.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * (功能描述)
 * Redis工具类
 * @Author:Dcl_Snow
 * @Create: 2022/7/25 15:53
 * @version: 1.0.0
 */
@Component
@Slf4j
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 向redis中存值
     * @param key 键
     * @param value 值
     * @return 是否存入成功
     */
    public boolean setValue(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("向redis中存入值时异常-->{}", e.getMessage());
            return false;
        }
    }

    /**
     * 向redis中存值并指定过期时间
     * SECONDS: 秒
     * MINUTES: 分
     * HOURS: 时
     * DAYS: 天
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setValueTime(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("设置缓存并指定过期时间异常-->{}", e.getMessage());
            return false;
        }
    }

    /**
     * 根据key获取redis中的值
     * @param key
     * @return
     */
    public Object getValue(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key删除redis中的缓存
     * @param keys 多个key
     * @return
     */
    public void delKey(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                for (String key : keys) {
                    redisTemplate.delete(key);
                }
            }
        }
    }

    /**
     * 判断值是否存在
     * @param key
     * @return
     */
    public boolean haskey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis值不存在-->{}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取redis键的过期时间
     * 0代表永久有效
     * 大于0就剩多少分钟失效
     * @param key
     * @return
     */
    public Long isExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MINUTES);
    }

    /**
     * 给key加过期时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.MINUTES);
            }
            return true;
        } catch (Exception e) {
            log.error("给旧的缓存设置新的过期时间异常--> {}", e.getMessage());
            return false;
        }
    }

}
