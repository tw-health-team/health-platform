package com.theus.health.base.service.system;

/**
 * 业务数据缓存处理
 *
 * @author tangwei
 * @date 2020-03-17 16:13
 */
public interface RedisService {
    /**
     * 移除用户缓存
     */
    public void removeUser(String username);
}
