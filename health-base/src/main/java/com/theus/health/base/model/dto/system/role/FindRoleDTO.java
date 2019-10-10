package com.theus.health.base.model.dto.system.role;

import com.theus.health.base.model.dto.BaseSplitPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tangwei
 * @version 2019/8/17/9:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FindRoleDTO extends BaseSplitPageDTO {

    private Boolean hasResource = true;

}
