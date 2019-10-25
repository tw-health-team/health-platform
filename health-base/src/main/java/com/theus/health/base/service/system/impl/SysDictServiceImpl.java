package com.theus.health.base.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.mapper.system.SysDictMapper;
import com.theus.health.base.model.dto.system.dict.FindDictDTO;
import com.theus.health.base.model.dto.system.dict.SysDictDTO;
import com.theus.health.base.model.po.system.SysDict;
import com.theus.health.base.service.system.SysDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangwei
 * @date 2019-10-07 22:16
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService{
    @Override
    public IPage<SysDict> findPage(FindDictDTO findDictDTO) {
        IPage<SysDict> dictPage = new Page<>(findDictDTO.getPageNum(),
                findDictDTO.getPageSize());

        dictPage.setRecords(this.baseMapper.findPage(dictPage,findDictDTO));
        return dictPage;
    }

    @Override
    public List<SysDict> findByLabel(String label) {
        return this.baseMapper.selectList(new QueryWrapper<SysDict>().eq("label", label));
    }

    @Override
    public List<SysDictDTO> findByType(String type) {
        List<SysDict> sysDictList = this.baseMapper.selectList(
                new QueryWrapper<SysDict>()
                        .eq("type", type)
                        .select("type","value","label")
                        .orderByAsc("sort"));
        List<SysDictDTO> sysDictDTOS = new ArrayList<>();
        sysDictList.forEach(v->{
            SysDictDTO sysDictDTO = new SysDictDTO();
            BeanUtils.copyProperties(v, sysDictDTO);
            sysDictDTOS.add(sysDictDTO);
        });
        return sysDictDTOS;
    }
}
