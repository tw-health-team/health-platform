package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 院内科室model
 * @author libin
 * @date 2019-11-4 14:42
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class SysDeptOrgan extends BaseModel implements Serializable {
    @TableId
    private String id;

    private String deptId;

    private String deptName;

    private String simpleSpelling;

    private String simpleWubi;

    private String organId;

    /**
     * 机构名称
     */
    @TableField(exist = false)
    private String organName;

    private String deptCenterId;

    /**
     * 中心科室名称
     */
    @TableField(exist = false)
    private  String deptCenterName;

    private String deptSocialSecurityId;

    private String outpatientType;

    /**
     * 门诊类型名称
     */
    @TableField(exist = false)
    private  String outpatientTypeName;

    private String registeredState;

    /**
     * 挂号状态名称
     */
    @TableField(exist = false)
    private  String registeredStateName;

    private String remarks;
}
