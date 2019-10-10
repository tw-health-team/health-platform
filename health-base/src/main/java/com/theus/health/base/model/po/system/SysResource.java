package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统权限资源
 *
 * @author tangwei
 * @date 2019-07-23 23:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysResource implements Serializable {

    @TableId
    private String id;

    private String name;

    private String parentId;

    private Short type;

    private String url;

    private String permission;

    private String color;

    private String icon;

    private Integer sort;

    private Boolean verification;

    private Date createDate;

    @TableField(exist = false)
    private String parentName;
    @TableField(exist = false)
    private Integer level;

    @TableField(exist = false)
    private List<SysResource> children;

}
