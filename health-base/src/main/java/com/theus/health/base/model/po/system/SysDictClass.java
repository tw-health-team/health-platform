package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 字典分类目录
 * @author tangwei
 * @date 2019-11-13 13:49
 */
@Data
public class SysDictClass {
    @TableId
    private String id;

    private String parentId;

    private String code;

    private String name;

    private String simpleSpelling;

    private Integer sort;

    private String remarks;
}
