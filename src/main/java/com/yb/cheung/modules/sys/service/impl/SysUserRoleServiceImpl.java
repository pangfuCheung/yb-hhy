package com.yb.cheung.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.cheung.modules.sys.dao.SysUserRoleDao;
import com.yb.cheung.modules.sys.entity.SysUserRole;
import com.yb.cheung.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {
}
