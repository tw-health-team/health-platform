package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.model.dto.system.dict.FindDictDTO;
import com.theus.health.base.model.po.system.SysDict;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-10-07 22:05
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 查询用户（分页）
     * @param findDictDTO 过滤条件
     * @return RequestResult
     */
    IPage<SysDict> findPage(FindDictDTO findDictDTO);

    /**
     * 根据名称查询--3333
     * @param label 字典项名称
     * @return 字典信息
     */
    List<SysDict> findByLabel(String label);
}
