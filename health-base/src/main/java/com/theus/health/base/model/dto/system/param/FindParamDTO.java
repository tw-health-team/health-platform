package com.theus.health.base.model.dto.system.param;

import com.theus.health.base.model.dto.BaseSplitPageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description TODO
 * @Author zgx
 * @Date 2019/10/25 15:22
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class FindParamDTO extends BaseSplitPageDTO {
    private String name;
}