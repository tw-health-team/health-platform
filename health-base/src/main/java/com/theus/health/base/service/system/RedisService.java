package com.theus.health.base.service.system;

import com.theus.health.base.model.dto.system.dict.SysDictDTO;
import com.theus.health.base.model.po.system.SysDict;
import com.theus.health.base.model.po.system.SysUser;

import java.util.List;

/**
 * 业务数据缓存处理
 *
 * @author tangwei
 * @date 2020-03-17 16:13
 */
public interface RedisService {

    /**
     * 获取用户缓存
     * @param username 用户名
     * @return 用户
     */
    SysUser getUser(String username);

    /**
     * 添加用户缓存
     * @param username 用户名
     * @param sysUser 用户
     */
    void addUser(String username, SysUser sysUser);

    /**
     * 移除用户缓存
     * @param username 用户名
     */
    void removeUser(String username);

    /**
     * 添加字典项缓存
     * @param classCode 字典分类代码
     * @param dictList 字典项list
     */
    void addDictItems(String classCode, List<SysDictDTO> dictList);

    /**
     * 删除字典项缓存
     * @param classCode 字典分类代码
     */
    void removeDictItem(String classCode);

    /**
     * 获取字典项缓存
     * @param classCode 字典分类代码
     * @return 字典项list
     */
    List<SysDictDTO> getDictItems(String classCode);
}
