package com.theus.health.base.model.dto.system.dict;

import lombok.Data;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-11-13 14:00
 */
@Data
public class DictClassDTO {
    private String id;

    private String code;

    private String name;

    private List<DictClassDTO> children;
}
