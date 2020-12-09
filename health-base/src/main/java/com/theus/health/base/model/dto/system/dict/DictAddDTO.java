package com.theus.health.base.model.dto.system.dict;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tangwei
 * @date 2019-11-14 14:44
 */
@Data
public class DictAddDTO {

    @NotBlank(message = "字典项值不能为空")
    private String itemValue;

    @NotBlank(message = "字典项名不能为空")
    private String itemName;

    @NotBlank(message = "字典分类代码不能为空")
    private String classCode;

    private Integer sort;

    private String remarks;
}
