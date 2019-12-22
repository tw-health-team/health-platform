package com.theus.health.base.model.po.system;

import lombok.Data;

import java.util.Date;

/**
 * 基础模型
 * @author tangwei
 * @date 2019-09-22 20:05
 */
@Data
public class BaseModel {

    private String createUserId;

    private String createUserName;

    private String createOrganId;

    private String createOrganName;

    private Date createTime;

    private String updateUserId;

    private String updateUserName;

    private String updateOrganId;

    private String updateOrganName;

    private Date updateTime;

    private Byte delFlag;
}
