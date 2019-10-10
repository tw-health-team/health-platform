package com.theus.health.base.model.dto.system.dept;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tangwei
 * @version 2019/9/25/20:50
 */
@Data
public class DeptUpdateDTO {

    @NotBlank(message = "机构名不能为空")
    private String name;

    @NotBlank(message = "父节点ID不能为空")
    private String parentId;

    private Integer orderNum;

    private Byte delFlag;

}
