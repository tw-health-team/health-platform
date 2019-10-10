package com.theus.health.base.model.dto.system.dept;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tangwei
 * @date 2019-09-25 20:54
 */
@Data
public class DeptAddDTO {
    @NotBlank(message = "机构名不能为空")
    private String name;

    @NotBlank(message = "父节点ID不能为空")
    private String parentId;

    private Integer orderNum;

    private Byte delFlag;
}
