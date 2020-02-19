package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 中心科室model
 * @author libin
 * @date 2019-10-24 19:53
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class SysDeptCenter extends BaseModel{

    @TableId
    private String id;

    private String name;

    private String simpleSpelling;

    private String simpleWubi;

    private String category;

    private String runk;

    private String remarks;

    private String parentId;

    @TableField(exist = false)
    private String parentName;

    /**
     * 科室类别名称
     */
    @TableField(exist = false)
    private String categoryName;

    /**
     * 科室级别名称
     */
    @TableField(exist = false)
    private String runkName;

    @TableField(exist = false)
    private Integer level;

    @TableField(exist = false)
    private List<SysDeptCenter> children;
}
