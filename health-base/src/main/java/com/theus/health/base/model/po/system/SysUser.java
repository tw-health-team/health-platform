package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 登录用户
 *
 * @author tangwei
 * @date 2019-03-14 16:40
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class SysUser extends BaseModel implements Serializable  {
    
    @TableId
    private String id;
    private String name;
    private Integer age;
    private String password;
    private String email;
    private String mobile;
    private Integer status;
    private String deptId;
    @TableField(exist = false)
    private String deptName;
    @TableField(exist = false)
    private List<SysRole> roles;

    private static final long serialVersionUID = 1L;

}
