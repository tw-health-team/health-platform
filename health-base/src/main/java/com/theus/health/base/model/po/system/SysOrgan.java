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
public class SysOrgan extends BaseModel implements Serializable {

	private String id;

    private String name;
    
    private String parentId;

	/**
	 * 机构名称简称
	 */
	private String shortName;

	/**
	 * 机构名称简拼
	 */
	private String simpleSpelling;

	/**
	 * 机构分类代码
	 */
    private String classificationCode;

	/**
	 * 机构级别代码
	 */
	private String levelCode;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 备注
	 */
	private String remarks;

    private Integer orderNum;

	/**
	 * 子机构列表
	 */
	@TableField(exist = false)
    private List<SysOrgan> children;

	/**
	 * 父机构名称
	 */
	@TableField(exist = false)
	private String parentName;

	/**
	 * 机构级别名称
	 */
	@TableField(exist = false)
	private String levelName;

	/**
	 * 机构类别名称
	 */
	@TableField(exist = false)
	private String classificationName;

	/**
	 * 机构树层数
	 */
	@TableField(exist = false)
	private Integer treeLayer;

	private static final long serialVersionUID = 1L;
}