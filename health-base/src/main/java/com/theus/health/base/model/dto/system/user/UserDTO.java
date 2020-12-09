package com.theus.health.base.model.dto.system.user;

import lombok.Data;

/**
 * @author tangwei
 * @date 2020-03-18 17:50
 */
@Data
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String realName;
    private String mobile;
    private String organId;
    private String organName;
}
