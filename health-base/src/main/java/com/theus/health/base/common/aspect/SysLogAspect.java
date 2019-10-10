package com.theus.health.base.common.aspect;

import com.alibaba.fastjson.JSON;
import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.config.jwt.JwtToken;
import com.theus.health.base.model.po.system.SysLog;
import com.theus.health.base.model.dto.SignInDTO;
import com.theus.health.base.model.dto.system.user.ResetPasswordDTO;
import com.theus.health.base.model.dto.system.user.UserAddDTO;
import com.theus.health.base.service.system.SysLogService;
import com.theus.health.core.util.RequestUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author tangwei
 * @date 2019-07-28 21:07
 */
@Aspect
@Component
public class SysLogAspect {

    private final SysLogService sysLogService;

    @Autowired
    public SysLogAspect(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Pointcut("@annotation(com.theus.health.base.common.annotation.SysLogs)")
    public void log() {
    }

    @AfterReturning("log()")
    public void after(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();
        }
        PrincipalCollection spc = null;
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipals() != null) {
            spc = subject.getPrincipals();
        }
        SysLog sysLog = new SysLog();
        //获取动作Action释义
        sysLog.setActionName(getMethodSysLogsAnnotationValue(joinPoint));
        //获取IP
        sysLog.setIp(RequestUtil.getClientIp(request));
        sysLog.setAjax(RequestUtil.isAjax(request) ? 1 : 0);
        sysLog.setUri(request == null ? "" : request.getRequestURI());
        String s = this.paramFilter(joinPoint.getArgs());
        //根据系统需求自定义
        sysLog.setParams(s.length() > 500 ? "数据过大，不给予记录" : s);
        sysLog.setHttpMethod(request == null ? "" : request.getMethod());
        sysLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName() + "()");
        //判断身份是否为空
        if (spc != null) {
            JwtToken jwtToken = new JwtToken();
            BeanUtils.copyProperties(spc.getPrimaryPrincipal(), jwtToken);
            sysLog.setUsername(jwtToken.getUsername());
            sysLog.setUid(jwtToken.getUid());
        } else {
            sysLog.setUsername("游客");
            sysLog.setUid("0");
        }
        sysLog.setCreateDate(new Date());
        sysLogService.save(sysLog);
    }

    private String getMethodSysLogsAnnotationValue(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(SysLogs.class)) {
            //获取方法上注解中表明的权限
            SysLogs sysLogs = method.getAnnotation(SysLogs.class);
            return sysLogs.value();
        }
        return "未知";
    }

    private String paramFilter(Object[] params) {
        //判断是否含有密码敏感数据
        final String filterString = "******";
        if (params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof SignInDTO) {
                    SignInDTO sign = (SignInDTO) params[i];
                    sign.setPassword(filterString);
                    params[i] = sign;
                }
                if (params[i] instanceof UserAddDTO) {
                    UserAddDTO userAddDTO = (UserAddDTO) params[i];
                    userAddDTO.setPassword(filterString);
                    params[i] = userAddDTO;
                }
                if (params[i] instanceof ResetPasswordDTO) {
                    ResetPasswordDTO resetPasswordDTO = (ResetPasswordDTO) params[i];
                    resetPasswordDTO.setPassword(filterString);
                    params[i] = resetPasswordDTO;
                }
            }
        }
        return JSON.toJSONString(params);
    }
}
