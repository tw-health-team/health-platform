package com.theus.health.base.model.dto.system.area;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 行政区划查询条件
 * @author libin
 * @date 2019-12-12 13:40
 */
@Data
public class FindAreaDTO {

    /**
     * 查询条件（名称、拼音码）
     */
    @NotBlank(message = "查询条件不能为空")
    private String searchText;

    @NotBlank(message = "上级区划ID不能为空")
    private String parentId;

}
