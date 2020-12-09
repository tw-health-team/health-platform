package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.theus.health.base.common.constants.RedisKeyPrefix;
import com.theus.health.base.model.dto.system.dict.SysDictDTO;
import com.theus.health.base.model.po.system.SysUser;
import com.theus.health.base.service.system.RedisService;
import com.theus.health.base.util.RedisUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public SysUser getUser(String username) {
        String userKey = RedisKeyPrefix.USER + username;
        SysUser sysUser = null;
        try {
            // 获取redis中缓存的用户
            if (redisUtil.existKey(userKey)) {
                sysUser = (SysUser) redisUtil.getObject(userKey);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sysUser;
    }

    @Override
    public void addUser(String username, SysUser sysUser) {
        String dictKey = RedisKeyPrefix.USER + username;
        // 过期时间设置为3天
        redisUtil.add(dictKey, sysUser, 3, TimeUnit.DAYS);
    }

    @Override
    public void removeUser(String username) {
        String userKey = RedisKeyPrefix.USER + username;
        // 获取redis中缓存的用户
        if (redisUtil.existKey(userKey)) {
            redisUtil.delete(userKey);
        }
    }

    @Override
    public void addDictItems(String classCode, List<SysDictDTO> dictList) {
        String dictKey = RedisKeyPrefix.DICT + classCode;
        removeDictItem(classCode);
        // 过期时间设置为7天
        redisUtil.add(dictKey, dictList, 7, TimeUnit.DAYS);
    }

    @Override
    public void removeDictItem(String classCode) {
        String dictKey = RedisKeyPrefix.DICT + classCode;
        if (redisUtil.existKey(dictKey)) {
            redisUtil.delete(dictKey);
        }
    }

    @Override
    public List<SysDictDTO> getDictItems(String classCode) {
        String dictKey = RedisKeyPrefix.DICT + classCode;
        List<SysDictDTO> dictList = null;
        try {
            // 获取redis中缓存的字典
            if (redisUtil.existKey(dictKey)) {
                dictList = redisUtil.getList(dictKey, SysDictDTO.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dictList;
    }


}
