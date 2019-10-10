package com.theus.health.base.model.dto.system.role;

import com.theus.health.base.model.po.system.SysResource;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author tangwei
 * @version 2019/8/17/9:42
 */
@Data
public class RoleUpdateDTO {

    @NotBlank(message = "姓名不能为空")
    private String name;

    @Length(max = 100)
    private String remark;

    @Size(min = 1,message = "请至少选择一个权限资源")
    private List<SysResource> resources;

}
