package com.theus.health.empi.model.dto.registerRule;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author libin
 * @date 2020-3-20 16:34
 */
@Data
public class JbxxZcgzAddDTO {

    private String lsh;

    @NotBlank(message = "第一规则代码")
    @Size(max = 1,message = "第一规则代码长度不能超过1")
    private String dygzdm;

    @NotBlank(message = "第一规则名称")
    @Size(max = 50,message = "第一规则名称长度不能超过50")
    private String dygzmc;

    @NotBlank(message = "第二规则代码")
    @Size(max = 1,message = "第二规则代码长度不能超过1")
    private String degzdm;

    @NotBlank(message = "第二规则名称")
    @Size(max = 50,message = "第二规则名称长度不能超过50")
    private String degzmc;
}
