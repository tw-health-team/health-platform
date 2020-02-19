package com.theus.health.base.model.dto.system.deptOrgan;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author libin
 * @date 2019-12-9 21:35
 */
@Data
public class DeptOrganAddDTO {

    private String id;

    @NotBlank(message = "中心科室编码不能为空")
    @Size(max = 15,message = "中心科室代码长度不能超过64")
    private String deptId;

    @NotBlank(message = "中心科室名称不能为空")
    @Size(max = 50,message = "中心科室名称长度不能超过50")
    private String deptName;

    @NotBlank(message = "所属机构不能为空")
    private String organId;

    @NotBlank(message = "中心科室不能为空")
    private String deptCenterId;

    @NotBlank(message = "社保局科室代码不能为空")
    @Size(max = 50,message = "中心科室名称长度不能超过64")
    private String deptSocialSecurityId;

    @NotBlank(message = "门诊类型不能为空")
    private String outpatientType;

    @NotBlank(message = "挂号状态不能为空")
    private String registeredState;

    private String remarks;
}
