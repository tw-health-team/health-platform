package com.theus.health.base.model.po.system;

import lombok.Data;

import java.util.Date;

/**
 * 基础模型
 * @author tangwei
 * @date 2019-09-22 20:05
 */
@Data
class BaseModel {

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    private Byte delFlag;
}
