package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author libin
 * @date 2019-12-11 20:34
 */
@Data
//@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class SysArea {

    /**
     * 地址编码
     */
    @TableId
    private String id;

    /**
     * 地址名称
     */
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
    private String type;

    @TableField(exist = false)
    private String typeName;

    /**
     * 省市类型
     */
    private String provincesType;

    @TableField(exist = false)
    private String provincesTypeName;

    /**
     * 拼音码
     */
    private String simpleSpelling;

    /**
     * 五笔码
     */
    private String simpleWubi;

    /**
     * 上级地址编码
     */
    private String parentId;

    /**
     * 上级地址名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 所属机构编码
     */
    private String organId;

    /**
     * 所属机构名称
     */
    @TableField(exist = false)
    private String organName;

    /**
     * 删除标志 0正常 1删除
     */
    private Integer delFlag;

    /**
     * 年份
     */
    private String year;

    @TableField(exist = false)
    private Integer level;

    @TableField(exist = false)
    private List<SysArea> children;
}
