package com.theus.health.base.common.service;

/**
 * @author tangwei
 * @date 2019-08-17 11:53
 * @param <E> 控制器对象实体
 * @param <AD> 添加DTO
 * @param <UD> 更新DTO
 * @param <UID> 对象ID
 * @param <FD> 查找DTO
 */
public interface BaseService<E,AD,UD,UID,FD> extends
        CreateService<AD>,
        QueryService<E,FD>,
        DeleteService<UID>,
        UpdateService<UID,UD>{
}

