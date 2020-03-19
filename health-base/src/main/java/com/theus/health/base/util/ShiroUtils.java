package com.theus.health.base.util;

import com.theus.health.base.common.constants.SysConstants;
import com.theus.health.base.config.jwt.JwtToken;
import com.theus.health.base.model.po.system.BaseModel;
import com.theus.health.base.model.po.system.SysUser;
import com.theus.health.base.service.system.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Shiro相关工具类
 *
 * @author tangwei
 * @date 2019-10-17 15:15
 */
@Component
public class ShiroUtils {

    private static SysUserService userService;

    @Autowired
    public ShiroUtils(SysUserService sysUserService) {
        userService = sysUserService;
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录账户
     *
     * @return 登录用户
     */
    public static SysUser getUser() {
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        return userService.getCacheUser(token.getUsername());
    }

    /**
     * 获取当前机构是否是超级管理员
     *
     * @return 是否
     */
    public static Boolean isSuperAdmin() {
        SysUser sysUser = getUser();
        return sysUser != null && sysUser.getName().equals(SysConstants.SUPER_ADMIN);
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 插入创建人、创建机构、创建时间、修改人、修改机构、修改时间
     *
     * @param entity 实体
     * @param <T>    继承BaseModel的泛型
     */
    public static <T extends BaseModel> void setInsert(T entity) {
        SysUser user = getUser();
        if (user != null) {
            entity.setCreateUserId(user.getId());
            entity.setCreateUserName(user.getName());
            entity.setCreateOrganId(user.getOrganId());
            entity.setCreateOrganName(user.getOrganName());
            entity.setCreateTime(new Date());
            setUpdate(entity);
        }
    }

    /**
     * 插入修改人、修改机构、修改时间
     *
     * @param entity 实体
     * @param <T>    继承BaseModel的泛型
     */
    public static <T extends BaseModel> void setUpdate(T entity) {
        SysUser user = getUser();
        if (user != null) {
            entity.setUpdateUserId(user.getId());
            entity.setUpdateUserName(user.getName());
            entity.setUpdateOrganId(user.getOrganId());
            entity.setUpdateOrganName(user.getOrganName());
            entity.setUpdateTime(new Date());
        }
    }

}
