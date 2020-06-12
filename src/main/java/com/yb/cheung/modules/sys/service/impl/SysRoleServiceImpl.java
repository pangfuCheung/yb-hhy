package com.yb.cheung.modules.sys.service.impl;

import com.yb.cheung.common.auth.SecurityUtils;
import com.yb.cheung.common.utils.QW;
import com.yb.cheung.modules.sys.dao.SysRoleMenuDao;
import com.yb.cheung.modules.sys.entity.SysMenu;
import com.yb.cheung.modules.sys.entity.SysRoleMenu;
import com.yb.cheung.modules.sys.service.SysMenuService;
import com.yb.cheung.modules.sys.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.Query;

import com.yb.cheung.modules.sys.dao.SysRoleDao;
import com.yb.cheung.modules.sys.entity.SysRole;
import com.yb.cheung.modules.sys.service.SysRoleService;



@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public boolean isAdmin(String userId) {
        List<SysRole> list = baseMapper.findSysRoleByUserId(userId);
        if (list.size() > 0){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRole> page = this.page(
                new Query<SysRole>().getPage(params),
                QW.getQW(params,SysRole.class,true)
        );

        return new PageUtils(page);
    }

    @Override
    public void insert(SysRole sysRole) {
        save(sysRole);
    }

    @Override
    public void insertBatch(List<SysRole> sysRoles) {
        saveBatch(sysRoles);
    }

    @Override
    public void update(SysRole sysRole) {
        updateById(sysRole);
    }



    @Override
    public void updateBatch(List<SysRole> sysRoles) {
        updateBatchById(sysRoles);
    }

    @Override
    public void invalid(String id) {
        SysRole SysRole = getById(id);
        SysRole.setStatus(0);
        update(SysRole);
    }

    @Override
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            SysRole SysRole = getById(id);
            SysRole.setStatus(0);
            update(SysRole);
        }
    }

    @Override
    public void delete(String id) {
        SysRole SysRole = getById(id);
        SysRole.setStatus(-1);
        update(SysRole);
    }

    @Override
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            SysRole SysRole = getById(id);
            SysRole.setStatus(-1);
            update(SysRole);
        }
    }

    @Override
    public void allotMenus(SysRole sysRole) {
        String[] menuIds = sysRole.getMenuIds();
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        for (int i=0;i<menuIds.length;i++){
            SysRoleMenu roleMenu =  new SysRoleMenu();
            roleMenu.setRoleId(sysRole.getUuid());
            roleMenu.setMenuId(menuIds[i]);
            roleMenus.add(roleMenu);
        }
        sysRoleMenuService.saveBatch(roleMenus);
    }

    @Override
    public SysRole getSysRoleById(String uuid) {
        SysRole sysRole = super.getById(uuid);
        if (null == sysRole){
            return null;
        }
        List<SysMenu> allMenus = sysMenuService.findAllMenusByRoleId(sysRole.getUuid());
        sysRole.setMenuList(allMenus);
        List<SysMenu> menus = sysMenuService.findAllMenusByUserId(SecurityUtils.getUser().getUuid());
        String menuIds[] = new String[menus.size()];
        for (int i=0;i<menuIds.length;i++){
            menuIds[i] = menus.get(i).getUuid();
        }
        sysRole.setMenuIds(menuIds);
        return sysRole;
    }
}