package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 系统角色
 *
 * @author tangwei
 * @date 2019-07-23 22:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRole implements Serializable  {

    @TableId
    private String id;
    
    private String name;

    private String remark;

    @TableField(exist = false)
    private List<SysResource> resources;

    private static final long serialVersionUID = 1L;

}
