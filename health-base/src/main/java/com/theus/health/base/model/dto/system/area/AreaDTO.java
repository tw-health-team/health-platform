package com.theus.health.base.model.dto.system.area;

import com.theus.health.base.common.validator.AreaLengthConstraint;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 行政区划新增修改实体
 *
 * @author tangwei
 * @date 2020-02-23 10:21
 */
@Data
public class AreaDTO {
    @NotBlank(message = "编码不能为空")
    @AreaLengthConstraint
    private String id;

    @NotBlank(message = "名称不能为空")
    @Size(max = 50,message = "地址名称长度不能超过50")
    private String name;

    @NotBlank(message = "上级编码不能为空")
    private String parentId;

    private String shortName;

    private String cityCode;

    private String zipCode;

    private String lng;

    private String lat;

    private String remark;
}
