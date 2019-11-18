package com.theus.health.base.model.dto.system.dict;

import lombok.Data;

/**
 * @author tangwei
 * @date 2019-11-15 8:55
 */
@Data
public class DictExistsQueryDTO {

    private String id;

    private String classCode;

    private String itemName;

    private String itemValue;
}
