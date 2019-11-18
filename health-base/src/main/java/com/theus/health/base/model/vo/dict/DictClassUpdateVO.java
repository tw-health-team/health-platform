package com.theus.health.base.model.vo.dict;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author tangwei
 * @date 2019-11-16 21:53
 */
@Data
public class DictClassUpdateVO {

    @NotBlank(message = "ID不能为空")
    private String id;

    @NotBlank(message = "父分类ID不能为空")
    private String parentId;

    @NotBlank(message = "分类代码不能为空")
    @Size(max = 50,message = "分类代码长度不能超过50")
    private String code;

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 50,message = "分类名称长度不能超过50")
    private String name;

    private Integer sort;

    @Size(max = 200,message = "备注长度不能超过200")
    private String remarks;
}
