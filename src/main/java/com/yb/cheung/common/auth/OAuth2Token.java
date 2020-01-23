package com.yb.cheung.common.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 实现shiro的token接口在
 */
public class OAuth2Token implements AuthenticationToken {
    private JWT jwt;

    public OAuth2Token(JWT jwt){
        this.jwt = jwt;
    }

    @Override
    public Object getPrincipal() {
        return jwt;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }
}
