package com.theus.health.base.config.shiro;

import com.theus.health.base.config.jwt.JwtToken;
import com.theus.health.base.util.JwtUtil;
import com.theus.health.core.util.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author tangwei
 * @version 2019/08/03/22:32
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        JwtToken jwtToken = (JwtToken) token;
        Object accountCredentials = getCredentials(info);
        if (jwtToken.getPassword() != null) {
            Object tokenCredentials = Encrypt.md5(jwtToken.getPassword() + jwtToken.getUsername());
            if (!accountCredentials.equals(tokenCredentials)) {
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
