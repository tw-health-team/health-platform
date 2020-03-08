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
	 * 详细地址
	 */
	private String address;

	/**
	 * 所属辖区省编码
	 */
	private String locationProvinceCode;

	/**
	 * 所属辖区省名称
	 */
	private String locationProvinceName;

	/**
	 * 所属辖区市编码
	 */
	private String locationCityCode;

	/**
	 * 所属辖区市名称
	 */
	private String locationCityName;

	/**
	 * 所属辖区县编码
	 */
	private String locationDistrictCode;

	/**
	 * 所属辖区县名称
	 */
	private String locationDistrictName;

	/**
	 * 所属辖区乡编码
	 */
	private String locationTownCode;

	/**
	 * 所属辖区乡名称
	 */
	private String locationTownName;

	/**
	 * 所属辖区村编码
	 */
	private String locationCommitteeCode;

	/**
	 * 所属辖区村名称
	 */
	private String locationCommitteeName;

	/**
	 * 所属辖区编码
	 */
	private String locationCode;

	/**
	 * 所属辖区名称
	 */
	private String locationName;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 排序
	 */
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