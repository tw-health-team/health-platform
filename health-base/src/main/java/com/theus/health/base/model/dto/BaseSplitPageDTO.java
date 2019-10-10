package com.theus.health.base.model.dto;

import lombok.Data;

/**
 * @author tangwei
 * @version 2019/7/28/21:17
 */
@Data
public abstract class BaseSplitPageDTO {

    private Integer pageNum;

    private Integer pageSize;

    private Boolean asc = false;

}
