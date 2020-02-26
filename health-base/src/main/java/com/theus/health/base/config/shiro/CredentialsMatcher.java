package com.theus.health.base.config.shiro;

import com.theus.health.base.config.jwt.JwtToken;
import com.theus.health.base.util.JwtUtil;
import com.theus.health.core.util.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 凭证匹配器
 * @author tangwei
 * @version 2019/08/03/22:32
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * 匹配用户输入的token的凭证（未加密）与系统提供的凭证（已加密）
     * @param token 用户输入的token的凭证
     * @param info 系统提供的凭证
     * @return 匹配是否成功
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        JwtToken jwtToken = (JwtToken) token;
        Object accountCredentials = getCredentials(info);
        if (jwtToken.getPassword() != null) {
            Object tokenCredentials = Encrypt.md5(jwtToken.getPassword() + jwtToken.getUsername());
            if (!accountCredentials.equals(tokenCredentials)) {
                // IncorrectCredentialsException
                throw new DisabledAccountException("密码不正确！");
            }
        } else {
            boolean verify = JwtUtil.verify(jwtToken.getToken(), jwtToken.getUsername(), accountCredentials.toString());
            if (!verify) {
                throw new DisabledAccountException("verifyFail");
            }
        }
        return true;
    }

}
