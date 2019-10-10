package com.theus.health.base.service.global;

import com.theus.health.base.model.po.system.SysResource;

import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2019-07-24 19:34
 */
public interface ShiroService {

    /**
     * 获取拦截器数据
     * @return
     */
    Map<String,String> getFilterChainDefinitionMap();

    /**
     * 迭代所有的资源子集
     * @param resource 系统资源
     * @param permsList
     * @param anonList
     */
    void iterationAllResourceInToFilter(SysResource resource,
                                        List<String[]> permsList, List<String[]> anonList);

    /**
     * 重新加载权限
     */
    void reloadPerms();

    /**
     * 清除指定用户ID的授权信息
     * @param uid 用户ID
     * @param author 是否清空授权信息
     * @param out 是否清空session
     */
    void clearAuthByUserId(String uid,Boolean author, Boolean out);

    /**
     * 清除指定用户ID集合的授权信息
     * @param userList 用户ID集合
     * @param author 是否清空授权信息
     * @param out 是否清空session
     */
    void clearAuthByUserIdCollection(List<String> userList,Boolean author, Boolean out);
}
