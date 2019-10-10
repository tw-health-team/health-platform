package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.theus.health.base.model.po.system.SysResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-07-24 20:31
 */
@Mapper
@Repository
public interface SysResourceMapper extends BaseMapper<SysResource> {

    /**
     * 根据用户名查找菜单列表
     * @param username 用户名
     * @return 资源列表
     */
    List<SysResource> findByUserName(@Param(value="username") String username);
}
