package com.yb.cheung.common.base;

import com.yb.cheung.common.utils.HttpContextUtils;
import com.yb.cheung.common.utils.RedisUtils;
import com.yb.cheung.modules.sys.entity.SysRole;
import com.yb.cheung.modules.sys.entity.SysUser;
import com.yb.cheung.modules.sys.service.SysRoleService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    public SysUser getUser(){
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }
        return token;
    }

    public boolean isAdmin(){
        return sysRoleService.isAdmin(getUser().getUuid());
    }

    public boolean isAdmin(String userId){
        return sysRoleService.isAdmin(userId);
    }

}
