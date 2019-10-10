package com.theus.health.base.common.service;

/**
 * @author tangwei
 * @date 2019-08-17 11:54
 * @see BaseService 注释配置请参见BaseService
 */
public interface CreateService<AD> {

    /**
     * 添加对象
     * @param addDTO 实体
     */
    void add(AD addDTO);

}
