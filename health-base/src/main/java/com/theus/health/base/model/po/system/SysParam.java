package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 参数
 *
 * @author zgx
 * @date 2019-10-24 20:13
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class SysParam extends BaseModel implements Serializable {
    @TableId
    private String id;

    private String name;

    private String value;

    private String maxValue;

    private String minValue;

    private String description;

    private String valueDescription;

    private String inputTypeCode;

    private String valueTypeCode;

    private String getValueSql;

    private String paramTypeCode;

//    private static final long serialVersionUID = 1L;
}