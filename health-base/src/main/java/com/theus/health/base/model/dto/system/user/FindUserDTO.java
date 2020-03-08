package com.theus.health.base.model.dto.system.user;

import com.theus.health.base.model.dto.BaseSplitPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tangwei
 * @date 2019-08-11 20:25
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class FindUserDTO extends BaseSplitPageDTO {

    /**
     * 用户名
     */
    private String name;

    /**
     * 机构ID
     */
    private String organId;

    /**
     * 是否显示下级机构用户
     */
    private int showChild;
}
