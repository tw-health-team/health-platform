package com.theus.health.base.util;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

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
    public void add(String key, Object value) {
        try {
            if (value instanceof String) {
                redisTemplate.opsForValue().set(key, value);
            } else if (value instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) value;
                for (String hashKey : map.keySet()) {
                    redisTemplate.opsForHash().put(key, hashKey, map.get(hashKey));
                }
            } else if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                redisTemplate.opsForList().leftPushAll(key, list);
            } else if (value instanceof SortedSet) {
                SortedSet<Object> sortedSet = (SortedSet<Object>) value;
                for (Object object : sortedSet) {
                    redisTemplate.opsForZSet().add(key, object, 0);
                }
            } else if (value instanceof Set) {
                Set<Object> set = (Set<Object>) value;
                for (Object object : set) {
                    redisTemplate.opsForSet().add(key, object);
                }
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
