package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统角色资源
 *
 * @author tangwei
 * @date 2019-07-24 19:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRoleResource implements Serializable {

    @TableId
    private String id;

    private String roleId;

    private String resourceId;

    private static final long serialVersionUID = 1L;
}
