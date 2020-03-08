package com.theus.health.base.model.dto.system.organ;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author tangwei
 * @version 2019/9/25/20:50
 */
@Data
public class OrganUpdateDTO {

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
     * 所属辖区省编码
     */
    @Size(max = 12,message = "所属辖区省编码不能超过12")
    private String locationProvinceCode;

    /**
     * 所属辖区省名称
     */
    private String locationProvinceName;

    /**
     * 所属辖区市编码
     */
    @Size(max = 12,message = "所属辖区市编码不能超过12")
    private String locationCityCode;

    /**
     * 所属辖区市名称
     */
    private String locationCityName;

    /**
     * 所属辖区县编码
     */
    @Size(max = 12,message = "所属辖区区县编码不能超过12")
    private String locationDistrictCode;

    /**
     * 所属辖区县名称
     */
    private String locationDistrictName;

    /**
     * 所属辖区乡编码
     */
    @Size(max = 12,message = "所属辖区乡编码不能超过12")
    private String locationTownCode;

    /**
     * 所属辖区乡名称
     */
    private String locationTownName;

    /**
     * 所属辖区村编码
     */
    @Size(max = 12,message = "所属辖区村编码不能超过12")
    private String locationCommitteeCode;

    /**
     * 所属辖区村名称
     */
    private String locationCommitteeName;

    /**
     * 备注
     */
    @Size(max = 200,message = "备注长度不能超过200")
    private String remarks;

    @NotNull(message = "排序序号不能为空")
    @Min(value = 1)
    private Integer orderNum;

}
