package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统字典
 *
 * @author tangwei
 * @date 2019-10-07 22:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDict {

	@TableId
	private String id;

    private String itemValue;

    private String itemName;

    private String simpleSpelling;

    private String simpleWubi;

    private String classCode;

    private Integer sort;

    private String remarks;

}