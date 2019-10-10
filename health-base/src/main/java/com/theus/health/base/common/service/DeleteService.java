package com.theus.health.base.common.service;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-08-17 11:54
 * @see BaseService 注释配置请参见BaseService
 */
public interface DeleteService<UID> {

    /**
     * 删除对象
     * @param id 主键
     */
    void remove(UID id);


}
