package com.theus.health.base.model.dto.system.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tangwei
 * @version 2019-08-24 22:41
 */
@Data
public class ResourceDTO {

    @NotBlank(message = "资源名称不能为空")
    private String name;

    private String parentId;

    @NotNull(message = "资源类型不能为空")
    private Short type;

    private String url;

    private String color;

    private String permission;

    private String icon;

    private String systemId;

    @NotNull(message = "资源排序不能为空")
    private Integer sort;

    private Boolean verification;

    /**
     * 是否生成默认按钮(0否 1是）
     */
    private String autoCreateButton;
}
