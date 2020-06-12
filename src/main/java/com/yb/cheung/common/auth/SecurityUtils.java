package com.yb.cheung.common.auth;

import com.alibaba.fastjson.JSON;
import com.yb.cheung.common.utils.Constant;
import com.yb.cheung.common.utils.RedisUtils;
import com.yb.cheung.modules.sys.entity.SysUser;
import com.yb.cheung.modules.sys.service.SysRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

    @Autowired
    private RedisUtils redisUtils;


    public static HttpServletRequest getCurrentRequest() throws IllegalStateException {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new IllegalStateException("当前线程中不存在 Request 上下文");
        }
        return attrs.getRequest();
    }

    public static SysUser getUser(){
        return (SysUser) org.apache.shiro.SecurityUtils.getSubject().getPrincipal();
    }

    private static String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }
        return token;
    }

    public static boolean isAdmin(){
        if (StringUtils.isBlank(getUser().getIsAdmin()) && getUser().getIsAdmin().equals(Constant.ENTITY_FIELD_IS_ADMIN_Y)){
            return true;
        } else {
            return false;
        }
    }

}
