package com.theus.health.base.model.dto.system.deptCenter;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author libin
 * @date 2019-12-9 16:25
 */
@Data
public class DeptCenterAddDTO {

    @NotBlank(message = "中心科室编码不能为空")
    @Size(max = 15,message = "中心科室代码长度不能超过64")
    private String id;

    @NotBlank(message = "中心科室名称不能为空")
    @Size(max = 50,message = "中心科室名称长度不能超过50")
    private String name;

    @NotBlank(message = "科室类别不能为空")
    private String category;

    @NotBlank(message = "科室级别不能为空")
    private String runk;

    private String remarks;

    private String parentId;
}
