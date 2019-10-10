package com.theus.health.base.util;

import com.theus.health.core.bean.ResponseCode;
import com.theus.health.base.config.jwt.JwtToken;
import com.theus.health.core.exception.BusinessException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author tangwei
 * @date 2019-06-15 18:43
 */
public class LoginUtil {
    public static boolean executeLogin(ServletRequest request){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if(authorization==null || "".equals(authorization.trim())){
            throw BusinessException.fail("未含授权标示，禁止访问");
        }
        JwtToken token = new JwtToken(authorization,null,null);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        }catch (DisabledAccountException e){
            String msg = "verifyFail";
            if(msg.equals(e.getMessage())){
                throw new BusinessException(ResponseCode.NOT_SING_IN.code,"身份已过期，请重新登录",e);
            }
            throw new BusinessException(ResponseCode.SIGN_IN_INPUT_FAIL.code,e.getMessage(),e);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ResponseCode.SIGN_IN_FAIL,e);
        }
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }
}
