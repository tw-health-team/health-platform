package com.theus.health.base.model.dto.system.dict;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tangwei
 * @date 2019-11-14 14:45
 */
@Data
public class DictUpdateDTO {

    @NotBlank(message = "字典项ID不能为空")
    private String id;

    @NotBlank(message = "字典项值不能为空")
    private String itemValue;

    @NotBlank(message = "字典项名不能为空")
    private String itemName;

    @NotBlank(message = "字典分类代码不能为空")
    private String classCode;

    private Long sort;

    private String remarks;
}
