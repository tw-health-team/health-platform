package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 行政区划
 * @author tangwei
 * @date 2020-02-23 10:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysArea {

    /**
     * 区划ID
     */
    @TableId
    private String id;

    /**
     * 全称
     */
    private String name;

    /**
     * 父ID
     */
    private String parentId;

    /**
     * 完整id路径(11000,110100,110105)
     */
    private String entirePath;

    /**
     * 完整名称(安徽/合肥市/庐阳区)
     */
    private String entireName;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 区划级别编码
     */
    private String levelType;

    /**
     * 省（自治区、直辖市）全称
     */
    private String province;

    /**
     * 市（地区）全称
     */
    private String city;

    /**
     * 县（区）全称
     */
    private String district;

    /**
     * 乡（镇、街道）全称
     */
    private String town;

    /**
     * 村（居委）
     */
    private String committee;

    /**
     * 省（自治区、直辖市）简称
     */
    private String provinceShortName;

    /**
     * 市（地区）简称
     */
    private String cityShortName;

    /**
     * 县（区）简称
     */
    private String districtShortName;

    /**
     * 乡（镇、街道）简称
     */
    private String townShortName;

    /**
     * 村（居委）简称
     */
    private String committeeShortName;

    /**
     * 省（自治区、直辖市）拼音
     */
    private String provinceSpelling;

    /**
     * 市（地区）拼音
     */
    private String citySpelling;

    /**
     * 县（区）拼音
     */
    private String districtSpelling;

    /**
     * 乡（镇、街道）拼音
     */
    private String townSpelling;

    /**
     * 村（居委）拼音
     */
    private String committeeSpelling;

    /**
     * 拼音
     */
    private String fullSpelling;

    /**
     * 简拼
     */
    private String simpleSpelling;

    /**
     * 首拼
     */
    private String firstChar;

    /**
     * 区号
     */
    private String cityCode;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否有子节点(0 无 1 有）
     */
    @TableField(exist = false)
    private int hasChildren;
}
