package com.theus.health.base.model.vo.dict;

import lombok.Data;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-11-16 21:09
 */
@Data
public class DictClassVO {
    private String id;

    private String parentId;

    /**
     * 父级分类名称
     */
    private String parentName;

    private String code;

    private String name;

    private Integer sort;

    private String remarks;

    private List<DictClassVO> children;
}
