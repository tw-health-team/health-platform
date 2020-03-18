package com.theus.health.base.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.theus.health.base.config.jwt.JwtToken;
import com.theus.health.base.model.po.system.SysUser;
import com.theus.health.base.service.system.SysUserService;
import com.theus.health.base.util.JwtUtil;
import com.theus.health.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 域，Shiro从从Realm获取安全数据（如用户、角色、权限）
 * 就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；
 * 也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看成DataSource，即安全数据源。
 * 可以有1个或多个Realm
 *
 * @author tangwei
 * @date 2019-06-15 21:15
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Resource
    private SysUserService userService;

    @Resource
    private CacheManager cacheManager;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权-权限验证(接口保护，验证接口调用权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("Shiro权限验证执行");
        JwtToken jwtToken = new JwtToken();
        BeanUtils.copyProperties(principalCollection.getPrimaryPrincipal(), jwtToken);
        if (jwtToken.getUsername() != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            SysUser findUser = userService.findUserByName(jwtToken.getUsername(), true);
            if (findUser != null) {
                if (findUser.getRoles() != null) {
                    findUser.getRoles().forEach(role -> {
                        info.addRole(role.getName());
                        if (role.getResources() != null) {
                            role.getResources().forEach(v -> {
                                if (!"".equals(v.getPermission().trim())) {
                                    info.addStringPermission(v.getPermission());
                                }
                            });
                        }
                    });
                }
                return info;
            }
        }
        throw new DisabledAccountException("用户信息异常，请重新登录！");
    }

    /**
     * 身份认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        SysUser user;
        String username = jwtToken.getUsername() != null ? jwtToken.getUsername() : JwtUtil.getUsername(jwtToken.getToken());
        try {
            user = userService.findUserByName(username,false);
        } catch (BusinessException e) {
            throw new DisabledAccountException(e.getMsg());
        }
        if (user == null) {
            throw new DisabledAccountException("用户不存在！");
        }
        if (user.getStatus() != 1) {
            throw new LockedAccountException("用户账户已锁定，暂无法登陆！");
        }
        if (jwtToken.getUsername() == null) {
            jwtToken.setUsername(user.getName());
        }
        // 生成token
        String token = JwtUtil.sign(user.getId(), user.getName(), user.getPassword());
        if (jwtToken.getToken() == null) {
            jwtToken.setToken(token);
        }
        jwtToken.setUid(user.getId());
        return new SimpleAuthenticationInfo(jwtToken, user.getPassword(), user.getId());
    }

    public void clearAuthByUserId(String uid, Boolean author, Boolean out) {
        //获取所有session
        Cache<Object, Object> cache = cacheManager
                .getCache(MyRealm.class.getName() + ".authorizationCache");
        cache.remove(uid);
    }

    public void clearAuthByUserIdCollection(List<String> userList, Boolean author, Boolean out) {
        Cache<Object, Object> cache = cacheManager
                .getCache(MyRealm.class.getName() + ".authorizationCache");
        userList.forEach(cache::remove);
    }
}
