package com.theus.health.base.util;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * String: 字符串
 * Hash: 散列
 * List: 列表
 * Set: 集合
 * Sorted Set: 有序集合
 *
 * @author tangwei
 * @date 2019-12-21 22：13
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean existKey(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }

    /**
     * 删除缓存
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 向Redis写入数据
     *
     * @param key   主键
     * @param value String、Map、List、SortedSet、Set
     */
    @SuppressWarnings("unchecked")
    public void add(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            if (value instanceof String) {
                if (timeout > 0) {
                    redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
                } else {
                    redisTemplate.opsForValue().set(key, value);
                }
            } else if (value instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) value;
                for (String hashKey : map.keySet()) {
                    redisTemplate.opsForHash().put(key, hashKey, map.get(hashKey));
                    this.expire(key, timeout, timeUnit);
                }
            } else if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                redisTemplate.opsForList().leftPushAll(key, list);
                this.expire(key, timeout, timeUnit);
            } else if (value instanceof SortedSet) {
                SortedSet<Object> sortedSet = (SortedSet<Object>) value;
                for (Object object : sortedSet) {
                    redisTemplate.opsForZSet().add(key, object, 0);
                    this.expire(key, timeout, timeUnit);
                }
            } else if (value instanceof Set) {
                Set<Object> set = (Set<Object>) value;
                for (Object object : set) {
                    redisTemplate.opsForSet().add(key, object);
                    this.expire(key, timeout, timeUnit);
                }
            } else {
                if (timeout > 0) {
                    redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
                } else {
                    redisTemplate.opsForValue().set(key, value);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间
     * @param timeUnit 时间单位
     */
    private void expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从Redis中取出Object类型的数据
     *
     * @param key 主键
     * @return String类型的值
     */
    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 从Redis中取出实体list数据
     *
     * @param cacheId     主键
     * @param targetClass 泛型类
     * @param <T>         声明泛型
     * @return list
     */
    public <T> List<T> getList(String cacheId, Class<T> targetClass) {
        List<T> list = new ArrayList<>();
        List<Object> objectList = redisTemplate.opsForList().range(cacheId, 0, -1);
        if (objectList != null) {
            objectList.forEach(v -> {
                if (targetClass.isInstance(v)) {
                    list.add(targetClass.cast(v));
                }
            });
        }
        return list;
    }

    /**
     * 从Redis中取出String类型的数据
     *
     * @param key 主键
     * @return String类型的值
     */
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 从Redis中取出Hash类型的数据
     *
     * @param key  主键
     * @param <HK> HashMap的key
     * @param <HV> <HV> HashMap的value
     * @return Hash类型的值
     */
    public <HK, HV> BoundHashOperations<String, HK, HV> getHash(String key) {
        return redisTemplate.boundHashOps(key);
    }

    /**
     * 从Redis中取出List类型的数据
     *
     * @param key 主键
     * @return List类型的值
     */
    public BoundListOperations<String, Object> getList(String key) {
        return redisTemplate.boundListOps(key);
    }

    /**
     * 从Redis中取出Set类型的数据
     *
     * @param key 主键
     * @return Set类型的值
     */
    public BoundSetOperations<String, Object> getSet(String key) {
        return redisTemplate.boundSetOps(key);
    }

    /**
     * 从Redis中取出Zset类型的数据
     *
     * @param key 主键
     * @return ZSet类型的值
     */
    public BoundZSetOperations<String, Object> getSortedSet(String key) {
        return redisTemplate.boundZSetOps(key);
    }
}
