package com.theus.health.base.model.vo.area;

import lombok.Data;

import java.util.List;

/**
 * 行政区划树形实体
 * @author tangwei
 * @date 2020-02-23 14:16
 */
@Data
public class AreaTreeVO {
    private String id;

    private String name;

    private String parentId;

    private String shortName;

    private String simpleSpelling;

    private String levelType;

    private String remark;

    private String parentName;

    /**
     * 上级完整名称
     */
    private String parentEntireName;

    /**
     * 完整名称
     */
    private String entireName;

    private List<AreaTreeVO> children;

    /**
     * 是否有子节点(0 无 1 有）
     */
    private int hasChildren;
}
