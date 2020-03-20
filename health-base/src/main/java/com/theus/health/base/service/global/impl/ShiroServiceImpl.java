package com.theus.health.base.service.global.impl;

import com.theus.health.base.config.shiro.MyRealm;
import com.theus.health.base.model.po.system.SysResource;
import com.theus.health.base.service.global.ShiroService;
import com.theus.health.base.service.system.SysResourceService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.core.util.SpringUtil;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2019-07-24 19:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShiroServiceImpl implements ShiroService {

    @Resource
    private SysResourceService resourceService;

    @Override
    public Map<String, String> getFilterChainDefinitionMap() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        List<String[]> permsList = new LinkedList<>();
        List<String[]> anonList = new LinkedList<>();

        List<SysResource> resources = resourceService.findAllResource();
        if (resources != null) {
            for (SysResource resource : resources) {
                if (!StringUtils.isEmpty(resource.getUrl()) && !StringUtils.isEmpty(resource.getPermission())) {
                    if (!"".equals(resource.getPermission().trim())) {
                        //判断是否需要权限验证
                        isNeedVerify(permsList, anonList, resource);
                    }
                }
                iterationAllResourceInToFilter(resource, permsList, anonList);
            }
        }
        for (String[] strings : anonList) {
            filterChainDefinitionMap.put(strings[0], strings[1]);
        }
        for (String[] strings : permsList) {
            filterChainDefinitionMap.put(strings[0], strings[1]);
        }
        filterChainDefinitionMap.put("/**", "anon");
        return filterChainDefinitionMap;
    }

    @Override
    public void iterationAllResourceInToFilter(SysResource resource,
                                               List<String[]> permsList, List<String[]> anonList) {
        if (resource.getChildren() != null && resource.getChildren().size() > 0) {
            for (SysResource v : resource.getChildren()) {
                if (!StringUtils.isEmpty(v.getUrl()) && !StringUtils.isEmpty(v.getPermission())) {
                    isNeedVerify(permsList, anonList, v);
                    iterationAllResourceInToFilter(v, permsList, anonList);
                }
            }
        }
    }

    private void isNeedVerify(List<String[]> permsList, List<String[]> anonList, SysResource v) {
        if (v.getVerification()) {
            permsList.add(0, new String[]{v.getUrl() + "/**", "perms[" + v.getPermission() + ":*]"});
        } else {
            anonList.add(0, new String[]{v.getUrl() + "/**", "anon"});
        }
    }

    @Override
    public void reloadPerms() {

        ShiroFilterFactoryBean shiroFilterFactoryBean = SpringUtil.getBean(ShiroFilterFactoryBean.class);

        AbstractShiroFilter abstractShiroFilter;
        try {
            abstractShiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.FAIL.code, "重新加载权限失败", e);
        }
        PathMatchingFilterChainResolver filterChainResolver = null;
        if (abstractShiroFilter != null) {
            filterChainResolver = (PathMatchingFilterChainResolver) abstractShiroFilter.getFilterChainResolver();
        }
        DefaultFilterChainManager manager = null;
        if (filterChainResolver != null) {
            manager = (DefaultFilterChainManager) filterChainResolver
                    .getFilterChainManager();
        }

        /*清除旧版权限*/
        if (manager != null) {
            manager.getFilterChains().clear();
        }
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

        /*更新新数据*/
        Map<String, String> filterChainDefinitionMap = getFilterChainDefinitionMap();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        if (manager != null) {
            filterChainDefinitionMap.forEach(manager::createChain);
        }
    }

    @Override
    public void clearAuthByUserId(String uid, Boolean author, Boolean out) {
        MyRealm myRealm = SpringUtil.getBean(MyRealm.class);
        myRealm.clearAuthByUserId(uid, author, out);
    }

    @Override
    public void clearAuthByUserIdCollection(List<String> userList, Boolean author, Boolean out) {
        MyRealm myRealm = SpringUtil.getBean(MyRealm.class);
        myRealm.clearAuthByUserIdCollection(userList, author, out);
    }
}
