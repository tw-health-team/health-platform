package com.theus.health.base.model.dto.system.resource;

import lombok.Data;

/**
 * 资源列表过滤条件
 * @author tangwei
 * @date 2020-03-17 14:17
 */
@Data
public class FindResourceDTO {
    /**
     * 资源名称、拼音简拼
     */
    private String searchText;
}
