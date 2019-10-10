package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户角色
 *
 * @author tangwei
 * @date 2019-07-24 19:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUserRole implements Serializable {

    @TableId
    private String id;

    private String userId;

    private String roleId;

}
