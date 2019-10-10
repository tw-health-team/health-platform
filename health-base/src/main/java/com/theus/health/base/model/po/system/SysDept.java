package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 机构
 *
 * @author tangwei
 * @date 2019-09-25 20:19
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class SysDept extends BaseModel implements Serializable {

	private String id;

    private String name;
    
    private String parentId;

    private Integer orderNum;

	@TableField(exist = false)
    private List<SysDept> children;

	@TableField(exist = false)
	private String parentName;
	@TableField(exist = false)
	private Integer level;

	private static final long serialVersionUID = 1L;
}