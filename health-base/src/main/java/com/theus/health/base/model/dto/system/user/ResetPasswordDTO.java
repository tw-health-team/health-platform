package com.theus.health.base.model.dto.system.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author tangwei
 * @date 2019-07-28 21:56
 */
@Data
public class ResetPasswordDTO {
    @NotBlank(message = "用户标识ID不能为空")
    private String uid;

    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp = "^(\\w){6,18}$",message = "密码应为[A-Za-z0-9_]组成的6-18位字符！")
    private String password;
}
