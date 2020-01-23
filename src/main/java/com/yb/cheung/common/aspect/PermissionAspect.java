package com.yb.cheung.common.aspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yb.cheung.common.auth.SecurityUtils;
import com.yb.cheung.common.utils.Constant;
import com.yb.cheung.common.utils.QW;
import com.yb.cheung.common.utils.RedisUtils;
import com.yb.cheung.modules.sys.entity.SysMenu;
import com.yb.cheung.modules.sys.entity.SysRole;
import com.yb.cheung.modules.sys.entity.SysUser;
import com.yb.cheung.modules.sys.entity.SysUserRole;
import com.yb.cheung.modules.sys.service.SysMenuService;
import com.yb.cheung.modules.sys.service.SysRoleMenuService;
import com.yb.cheung.modules.sys.service.SysRoleService;
import com.yb.cheung.modules.sys.service.SysUserRoleService;
import org.apache.shiro.authz.AuthorizationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*@Aspect
@Component*/
public class PermissionAspect {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private RedisUtils redisUtils;

    @Around("execution(* com.yb.cheung.modules.*.controller.*Controller.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = SecurityUtils.getCurrentRequest();
        String permissionStr = request.getRequestURI().replace(request.getContextPath()+"/","");
        SysUser sysUser = (SysUser) org.apache.shiro.SecurityUtils.getSubject().getPrincipal();
        Set<String> permssionSet = null;
        permssionSet = redisUtils.get(String.format(Constant.USER_MENU_KEY,sysUser.getUuid()),Set.class);
        if (null == permssionSet){
            permssionSet = new HashSet<>();
            List<SysMenu> menus =  sysMenuService.findAllMenusByUserId(sysUser.getUuid());
            for (SysMenu sysMenu:menus){
                if (null != sysMenu.getUrl()){
                    permssionSet.add(sysMenu.getUrl());
                }
                if (null != sysMenu.getPerms()){
                    permssionSet.add(sysMenu.getPerms());
                }
            }
            redisUtils.set(String.format(Constant.USER_MENU_KEY,sysUser.getUuid()),permssionSet);
        }
        if (permssionSet.contains(permissionStr)){
            return point.proceed();
        }
        throw new AuthorizationException("没有权限");
    }

}
