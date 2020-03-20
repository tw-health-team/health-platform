package com.theus.health.base.service.system.impl;

import com.theus.health.base.common.constants.RedisKeyPrefix;
import com.theus.health.base.service.system.RedisService;
import com.theus.health.base.util.RedisUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 业务数据缓存处理
 *
 * @author tangwei
 * @date 2020-03-17 16:13
 */
@Repository
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisUtil redisUtil;

    @Override
    public void removeUser(String username) {
        String userKey = RedisKeyPrefix.USER + username;
        // 获取redis中缓存的用户
        if (redisUtil.existKey(userKey)) {
            redisUtil.delete(userKey);
        }
    }
}
