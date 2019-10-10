package com.theus.health.base.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author tangwei
 * @date 2019-08-17 11:54
 * @see BaseService 注释配置请参见BaseService
 */
public interface QueryService<E,FD> {

    /**
     * 查找对象
     * @param findDTO 条件实体
     * @return list
     */
    IPage<E> list(FD findDTO);

}