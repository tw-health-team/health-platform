package com.theus.health.base.model.dto.system.log;

import com.theus.health.base.model.dto.BaseSplitPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tangwei
 * @date 2019-07-28 21:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FindLogDTO extends BaseSplitPageDTO {

    private String name;
}

