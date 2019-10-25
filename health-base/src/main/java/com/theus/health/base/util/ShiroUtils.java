package com.theus.health.base.util;

import com.theus.health.base.config.jwt.JwtToken;
import com.theus.health.base.model.po.system.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;

/**
 * Shiro相关工具类
 * @author tangwei
 * @date 2019-10-17 15:15
 */
public class ShiroUtils {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static SysUser getUser() {
		JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
		SysUser sysUser = new SysUser();
		sysUser.setName(token.getUsername());
		sysUser.setId(token.getUid());
		sysUser.setPassword(token.getPassword());
		return sysUser;
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
	
}
