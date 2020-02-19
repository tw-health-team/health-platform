package com.theus.health.base.model.dto.system.area;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 新增地址信息
 * @author libin
 * @date 2019-12-12 13:48
 */
@Data
public class AreaAddDTO {

    /**
     * 地址编码
     */
    @NotBlank(message = "地址编码不能为空")
    @Size(max = 15,message = "地址编码长度不能超过20")
    private String id;

    /**
     * 地址名称
     */
    @NotBlank(message = "地址名称不能为空")
    @Size(max = 50,message = "地址名称长度不能超过50")
    private String name;

    /**
     * 地址简称
     */
    private String shortName;

    /**
     * 地址全称
     */
    private String fullName;

    /**
     * 地址类型 1 省 2 市 3 区县 4 街道 5 居委
     */
    @NotBlank(message = "地址类型不能为空")
    private String type;

    /**
     * 省市类型
     */
    private String provincesType;

    /**
     * 上级地址编码
     */
    private String parentId;

    /**
     * 所属机构编码
     */
    private String organId;

    /**
     * 年份
     */
    private String year;
}
