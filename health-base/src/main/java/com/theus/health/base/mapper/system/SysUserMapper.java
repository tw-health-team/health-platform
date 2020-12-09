package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.theus.health.base.model.dto.system.user.FindUserDTO;
import com.theus.health.base.model.po.system.SysOrgan;
import com.theus.health.base.model.po.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-03-19 15:43
 */
@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户列表
     * @param page 分页
     * @param findUserDTO 查询条件
     * @return 用户list
     */
    List<SysUser> findPage(IPage<SysUser> page, @Param("user")FindUserDTO findUserDTO);

    /**
     * 查询机构用户
     * @param page 分页
     * @param findUserDTO 查询条件
     * @param organs 机构列表
     * @return 用户list
     */
    List<SysUser> getOrganUsers(IPage<SysUser> page, @Param("user")FindUserDTO findUserDTO, @Param("organs")List<SysOrgan> organs);

    /**
     * 获取用户信息
     * @param name 用户名
     * @return 用户信息
     */
    SysUser getUser(String name);
}
