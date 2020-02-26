package com.theus.health.base.common.constants;

/**
 * redis缓存键前缀
 * @author tangwei
 * @date 2019-12-09 22:51
 */
public interface RedisKeyPrefix {

    String SYSTEM = "health";
    String SEPARATOR = "_";
    /**
     * 系统管理员用户名
     */
    String USER = SYSTEM + SEPARATOR + "sys.user.";
}
