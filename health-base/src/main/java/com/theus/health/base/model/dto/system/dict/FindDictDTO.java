package com.theus.health.base.model.dto.system.dict;

import com.theus.health.base.model.dto.BaseSplitPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tangwei
 * @date 2019-10-07 22:06
 */
@Data
public class FindDictDTO {

    private String classCode;

    private String searchText;
}
