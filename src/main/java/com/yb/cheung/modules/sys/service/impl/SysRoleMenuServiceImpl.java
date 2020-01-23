package com.yb.cheung.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.cheung.modules.sys.dao.SysMenuDao;
import com.yb.cheung.modules.sys.dao.SysRoleMenuDao;
import com.yb.cheung.modules.sys.entity.SysMenu;
import com.yb.cheung.modules.sys.entity.SysRoleMenu;
import com.yb.cheung.modules.sys.service.SysMenuService;
import com.yb.cheung.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {
}
