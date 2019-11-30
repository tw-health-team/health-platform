package com.theus.health.base.model.dto.system.user;

import com.theus.health.base.model.po.system.SysRole;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author tangwei
 * @date 2019-07-28 21:55
 */
@Data
public class UserAddDTO {
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^(\\w){4,16}$",message = "用户名应为[A-Za-z0-9_]组成的4-16位字符！")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(\\w){6,18}$",message = "密码应为[A-Za-z0-9_]组成的6-18位字符！")
    private String password;

    private Integer age;

    private String email;

    private String mobile;

    private String organId;

    private String organName;

    @NotNull(message = "状态标识不能为空")
    private Integer status;

    @Size(min = 1, message = "请至少选择一个角色")
    private List<SysRole> roles;
}
