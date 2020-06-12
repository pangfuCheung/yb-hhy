package com.yb.cheung.modules.sys.service.impl;

import com.alibaba.druid.sql.visitor.functions.Concat;
import com.yb.cheung.common.auth.SecurityUtils;
import com.yb.cheung.common.utils.*;
import com.yb.cheung.modules.sys.entity.SysDept;
import com.yb.cheung.modules.sys.entity.SysRole;
import com.yb.cheung.modules.sys.entity.SysUser;
import com.yb.cheung.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yb.cheung.modules.sys.dao.SysMenuDao;
import com.yb.cheung.modules.sys.entity.SysMenu;
import com.yb.cheung.modules.sys.service.SysMenuService;



@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleService sysRoleService;


    @Override
    public List<SysMenu> findAllMenu() {
        return setMenus(super.list());
    }

    @Override
    public List<SysMenu> findAllMenuChildrens(String userId) {
        List<SysMenu> allList = baseMapper.findAllMenuChildrens(userId);
        boolean isAdmin = sysRoleService.isAdmin(userId);
        List<SysMenu> allMenus = new ArrayList<>();
        for (SysMenu sysMenu:allList){
            if (isAdmin && null != sysMenu.getStatus() && sysMenu.getStatus() != Constant.ENTITY_STATUS_DELETE){
                allMenus.add(sysMenu);
            }else if (null != sysMenu.getStatus() && sysMenu.getStatus() == Constant.ENTITY_STATUS_VALID){
                allMenus.add(sysMenu);
            }
        }

        List<SysMenu> allParents = new ArrayList<>();
        for (SysMenu sysMenu:allMenus){
            if (null != sysMenu && null == sysMenu.getParentId()){
                allParents.add(sysMenu);
            }
        }
        for (SysMenu sysMenu:allParents){
            setChildrens(sysMenu,allMenus);
        }
        return allParents;
    }

    private List<SysMenu> findMenusByWrapper(QueryWrapper queryWrapper){
        return setMenus(super.list(queryWrapper));
    }

    /**
     * 通过递归查询设置子菜单
     * @param menus
     * @return
     */
    private List<SysMenu> setMenus(List<SysMenu> menus){
        List<SysMenu> allParents = new ArrayList<>();
        for (SysMenu sysMenu:menus){
            if (null != sysMenu && null == sysMenu.getParentId()){
                allParents.add(sysMenu);
            }
        }
        for (SysMenu sysMenu:allParents){
            setChildrens(sysMenu,menus);
        }
        return allParents;
    }

    private void setChildrens(SysMenu sysMenu,List<SysMenu> allMenus){
        List<SysMenu> childrens = new ArrayList<>();
        for (SysMenu children:allMenus){
            if (sysMenu.getUuid().equals(children.getParentId())){
                childrens.add(children);
            }
        }
        if (childrens.size() > 0){
            sysMenu.setChildrens(childrens);
            for (SysMenu children:childrens){
                setChildrens(children,allMenus);
            }
        }
    }

    @Override
    public List<SysMenu> findAllMenus(Map<String, Object> param) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>();
        queryWrapper.in("status",Constant.orStatus);
        return findMenusByWrapper(queryWrapper);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysMenu> page = this.page(
                new Query<SysMenu>().getPage(params),
                QW.getQW(params, SysMenu.class,true)
        );
        return new PageUtils(page);
    }

    @Override
    public void insert(SysMenu sysMenu) {
        save(sysMenu);
    }

    @Override
    public void insertBatch(List<SysMenu> sysMenus) {
        saveBatch(sysMenus);
    }

    @Override
    public void update(SysMenu sysMenu) {
        updateById(sysMenu);
    }

    @Override
    public void updateBatch(List<SysMenu> sysMenus) {
        updateBatchById(sysMenus);
    }

    @Override
    public void invalid(String id) {
        SysMenu SysMenu = getById(id);
        SysMenu.setStatus(0);
        update(SysMenu);
    }

    @Override
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            SysMenu SysMenu = getById(id);
            SysMenu.setStatus(0);
            update(SysMenu);
        }
    }

    @Override
    public void delete(String id) {
        SysMenu SysMenu = getById(id);
        SysMenu.setStatus(-1);
        update(SysMenu);
    }

    @Override
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            SysMenu SysMenu = getById(id);
            SysMenu.setStatus(-1);
            update(SysMenu);
        }
    }

    @Override
    public List<SysMenu> findAllMenusByRoleId(String roleId) {
        return setMenus(baseMapper.findAllMenusByRoleId(roleId));
    }

    @Override
    public List<SysMenu> findAllMenusByUserId(String userId) {
        return baseMapper.findAllMenusByCurrentUser(userId);
    }
}