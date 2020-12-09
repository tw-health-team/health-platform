package com.theus.health.record.model.dto;

import com.theus.health.base.model.dto.BaseSplitPageDTO;
import lombok.Data;

/**
 * @author tangwei
 * @date 2020-03-25 15:10
 */
@Data
public class FindPersonalListDTO extends BaseSplitPageDTO {
    /**
     * 业务管理机构代码
     */
    private String manageOrganCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idCard;
}
