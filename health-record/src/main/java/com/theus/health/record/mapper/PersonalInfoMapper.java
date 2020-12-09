package com.theus.health.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.theus.health.record.model.dto.FindPersonalListDTO;
import com.theus.health.record.model.po.EhrPersonalInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tangwei
 * @date 2020-03-25 15:07
 */
@Mapper
@Repository
public interface PersonalInfoMapper extends BaseMapper<EhrPersonalInfo> {

    /**
     * 查找基本信息列表
     *
     * @param page                分页
     * @param findPersonalListDTO 查询条件
     * @return 用户list
     */
    List<EhrPersonalInfo> findPage(IPage<EhrPersonalInfo> page, @Param("query") FindPersonalListDTO findPersonalListDTO);

}
