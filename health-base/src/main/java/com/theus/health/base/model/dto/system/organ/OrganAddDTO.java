package com.theus.health.base.model.dto.system.organ;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author tangwei
 * @date 2019-09-25 20:54
 */
@Data
public class OrganAddDTO {
    @NotBlank(message = "机构编码不能为空")
    @Size(max = 15,message = "机构代码长度不能超过15")
    private String id;

    @NotBlank(message = "机构名不能为空")
    @Size(max = 50,message = "机构名称长度不能超过50")
    private String name;

    private String parentId;

    /**
     * 机构名称简称
     */
    @Size(max = 20,message = "机构简称长度不能超过20")
    private String shortName;

    @NotBlank(message = "机构分类不能为空")
    private String classificationCode;

    @NotBlank(message = "机构级别不能为空")
    private String levelCode;

    /**
     * 联系电话
     */
    @Size(max = 20,message = "联系电话长度不能超过20")
    private String phone;

    /**
     * 地址
     */
    @Size(max = 50,message = "地址长度不能超过50")
    private String address;

    /**
     * 备注
     */
    @Size(max = 200,message = "备注长度不能超过200")
    private String remarks;

    @NotNull(message = "排序序号不能为空")
    @Min(value = 1)
    private Integer orderNum;
}
