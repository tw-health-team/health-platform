package com.theus.health.core.exception;

import com.theus.health.core.bean.ResponseCode;
import lombok.*;

import java.io.Serializable;

/**
 * @author tangwei
 * @date 2019-06-15 19:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException implements Serializable {
    private Integer status;
    private String msg;
    private Exception e;

    public BusinessException(ResponseCode statusEnum, Exception e) {
        this.status = statusEnum.code;
        this.msg = statusEnum.msg;
        this.e = e;
    }


    public BusinessException(ResponseCode statusEnum) {
        this.status = statusEnum.code;
        this.msg = statusEnum.msg;
    }

    public synchronized static BusinessException fail(String msg){
        return BusinessException.builder()
                .status(ResponseCode.FAIL.code)
                .msg(msg)
                .build();
    }

    public synchronized static BusinessException fail(String msg,Exception e){
        return BusinessException.builder()
                .status(ResponseCode.FAIL.code)
                .msg(msg)
                .e(e)
                .build();
    }

    public synchronized static BusinessException fail(Integer code,String msg,Exception e){
        return BusinessException.builder()
                .status(code)
                .msg(msg)
                .e(e)
                .build();
    }
}
