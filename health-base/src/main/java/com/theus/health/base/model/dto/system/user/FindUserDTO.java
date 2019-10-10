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

    private String name;
}
