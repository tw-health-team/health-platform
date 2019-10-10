package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.theus.health.base.model.dto.system.dict.FindDictDTO;
import com.theus.health.base.model.po.system.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-10-07 22:18
 */
@Mapper
@Repository
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 查询用户列表
     * @param findUserDTO 查询条件
     * @return 用户list
     */
    List<SysDict> findPage(IPage<SysDict> page, @Param("param") FindDictDTO findUserDTO);
}

