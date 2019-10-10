package com.theus.health.base.model.po.system;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 *
 * @author tangwei
 * @date 2019-07-24 19:27
 */
@Data
public class SysLog implements Serializable {

    @TableId
    private String id;

    private String username;

    private String uid;

    private String ip;

    private Integer ajax;

    private String uri;

    private String params;

    private String httpMethod;

    private String classMethod;

    private String actionName;

    private Date createDate;


}
