package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.model.dto.system.user.ResetPasswordDTO;
import com.theus.health.base.model.dto.system.user.UserAddDTO;
import com.theus.health.base.model.dto.system.user.UserUpdateDTO;
import com.theus.health.base.model.po.system.SysResource;
import com.theus.health.base.model.po.system.SysRole;
import com.theus.health.base.model.po.system.SysUser;
import com.theus.health.base.model.dto.SignInDTO;
import com.theus.health.base.model.dto.system.user.FindUserDTO;
import com.theus.health.base.model.vo.SysUserVO;

import java.util.List;
import java.util.Set;

/**
 * @author tangwei
 * @date 2019/5/5 11:22
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查找用户
     * @param name 用户名
     * @param hasResource 是否包含权限
     * @return User
     */
    SysUser findUserByName(String name, boolean hasResource);

    /**
     * 根据ID查找用户
     * @param id ID
     * @param hasResource 是否包含权限
     * @return User
     */
    SysUser findUserById(String id,boolean hasResource);

    /**
     * 用户登录操作
     * @param signInDTO 登录信息
     */
    void signIn(SignInDTO signInDTO);

    /**
     * 获取当前登录用户信息
     * @return UserVO
     */
    SysUserVO getCurrentUser();

    /**
     * 用户角色资源匹配
     * @param roles 用户角色集
     * @return 资源集合
     */
    List<SysResource> userRolesRegexResource(List<SysRole> roles);

    /**
     * 获取所有用户（分页）
     * @param findUserDTO 过滤条件
     * @return RequestResult
     */
    IPage<SysUserVO> getAllUserBySplitPage(FindUserDTO findUserDTO);

    /**
     * 查询用户（分页）
     * @param findUserDTO 过滤条件
     * @return RequestResult
     */
    IPage<SysUser> findPage(FindUserDTO findUserDTO);

    /**
     * 用户状态改变
     * @param userId 用户ID
     * @param status 状态码
     */
    void statusChange(String userId, Integer status);

    /**
     * 删除用户
     * @param userId 用户ID
     */
    void removeUser(String userId);

    /**
     * 添加用户
     * @param addDTO 用户数据DTO
     */
    void add(UserAddDTO addDTO);

    /**
     * 更新用户数据
     * @param id 用户id
     * @param updateDTO 用户数据DTO
     */
    void update(String id, UserUpdateDTO updateDTO);

    /**
     * 更新用户角色关联
     * @param user 用户数据
     */
    void updateUserRole(SysUser user);

    /**
     * 重置用户密码
     * @param resetPasswordDTO 重置密码信息
     */
    void resetPassword(ResetPasswordDTO resetPasswordDTO);

    /**
     *查找用户的菜单权限标识集合
     * @param username 用户名
     * @return 菜单权限标识集合
     */
    Set<String> findPermissions(String username);
}
