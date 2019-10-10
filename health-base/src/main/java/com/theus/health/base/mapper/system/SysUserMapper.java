package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.theus.health.base.model.dto.system.user.FindUserDTO;
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
     * @param findUserDTO 查询条件
     * @return 用户list
     */
    List<SysUser> findPage(IPage<SysUser> page, @Param("user")FindUserDTO findUserDTO);
}
