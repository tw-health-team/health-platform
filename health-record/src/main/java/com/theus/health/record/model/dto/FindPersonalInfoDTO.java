package com.theus.health.record.model.dto;

import lombok.Data;

/**
 * 居民个人基本信息查询条件
 * @author tangwei
 * @date 2020-03-25 16:30
 */
@Data
public class FindPersonalInfoDTO {

    /**
     * 个人档案标识号
     */
    private String personalId;

    /**
     * 身份证号
     */
    private String idCard;
}
