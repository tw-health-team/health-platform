package com.theus.health.base.common.service;

/**
 * @author tangwei
 * @date 2019-08-17 11:54
 * @see BaseService 注释配置请参见BaseService
 */
public interface UpdateService<UID,UD> {

    /**
     * 更新对象
     * @param id 主键
     * @param updateDTO 修改实体
     */
    void update(UID id,UD updateDTO);

}
