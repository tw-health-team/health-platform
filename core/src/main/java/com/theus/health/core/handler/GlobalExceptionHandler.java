package com.theus.health.core.handler;

import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import com.theus.health.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-06-15 19:13
 */
@ControllerAdvice(basePackages = {"com.theus.health"})
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有不可知的异常
     *
     * @param e 异常
     * @return 通用http response bean
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseResult requestExceptionHandler(Exception e) {
        LOGGER.error(e.getMessage(), e);
        String msg;
        if (e.getCause() != null) {
            msg = e.getCause().getMessage();
        } else {
            msg = e.getMessage();
        }
        e.printStackTrace();
        return ResponseResult.builder().msg(msg).status(ResponseCode.FAIL.code).build();
    }

    /**
     * 处理所有业务异常
     *
     * @param e 异常
     * @return 通用http response bean
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    ResponseResult businessExceptionHandler(BusinessException e) {
        LOGGER.error(e.getMessage(), e);
        if (e.getE() != null) {
            e.printStackTrace();
        }
        return ResponseResult.builder().msg(e.getMsg()).status(e.getStatus()).build();
    }

    /**
     * 处理所有接口数据验证异常
     * --@Validated + @RequestBody 注解但是没有在绑定的数据对象后面跟上 Errors 类型的参数声明的话，
     * --Spring MVC 框架会抛出 MethodArgumentNotValidException 异常
     *
     * @param e 异常
     * @return 通用http response bean
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ResponseResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        LOGGER.error(e.getMessage(), e);
        BindingResult result = e.getBindingResult();
        String s = "参数验证失败";
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            s = errors.get(0).getDefaultMessage();
        }
        return ResponseResult.builder().status(ResponseCode.FAIL.code).msg(s).build();
    }

    /**
     * bean校验未通过异常
     *
     * @see javax.validation.Valid
     * @see org.springframework.validation.Validator
     * @see org.springframework.validation.DataBinder
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    ResponseResult validExceptionHandler(BindException e) {
        LOGGER.error(e.getMessage(), e);
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseResult.builder().status(ResponseCode.FAIL.code).msg(msg).build();
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseResult requestExceptionHandler(DataIntegrityViolationException e) {
        String msg = "数据操作格式异常：";
        if (e.getCause() != null) {
            msg += e.getCause().getMessage();
        } else {
            msg += e.getMessage();
        }
        return ResponseResult.builder().msg(msg).status(ResponseCode.FAIL.code).build();
    }
}

