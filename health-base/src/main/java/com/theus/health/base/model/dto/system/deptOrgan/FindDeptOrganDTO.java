package com.theus.health.base.model.dto.system.deptOrgan;

import com.theus.health.base.model.dto.BaseSplitPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 院内科室查询条件
 * @author libin
 * @date 2019-11-4 14:52
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class FindDeptOrganDTO extends BaseSplitPageDTO {

    private String organId;

    private String deptOrganId;

    private String deptOrganName;
}
