package com.theus.health.base.model.vo;

import com.theus.health.base.model.po.system.SysResource;
import com.theus.health.base.model.po.system.SysRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author tangwei
 * @date 2019-07-28 21:48
 */
@Data
public class SysUserVO {
    private String id;
    private String name;
    private Integer age;
    private Integer status;
    private List<SysRole> roles;
    private Date createDate;
    private List<SysResource> resources;
}
