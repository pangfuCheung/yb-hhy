package com.yb.cheung.common.auth;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Component;

@Component
public class OAuth2CredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * 校验账号密码
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        return true;
        //return super.doCredentialsMatch(token, info);
    }

}
