package com.theus.health.empi.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Empi注册规则
 * @author libin
 * @date 2020-3-20 15:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JbxxZcgz {
    @TableId
    private String lsh;

    private String dygzdm;

    private String dygzmc;

    private String degzdm;

    private String degzmc;

    private Date cjsj;
}
