package com.yb.cheung.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.modules.sys.entity.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * 系统菜单信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 19:47:50
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 首页菜单
     * @param userId
     * @return
     */
    List<SysMenu> findAllMenuChildrens(String userId);

    /**
     * 全部菜单
     * @return
     */
    List<SysMenu> findAllMenu();

    /**
     * 菜单展示页面接口
     * @param param
     * @return
     */
    List<SysMenu> findAllMenus(Map<String,Object> param);


    PageUtils queryPage(Map<String, Object> params);


    /**
     * 更新或者保存
     * @param sysMenu
     * @return
     */
    void update(SysMenu sysMenu);

    /**
     * 批量保存
     * @param sysLogs
     * @return
     */
    void updateBatch(List<SysMenu> sysLogs);

    /**
     * 插入数据
     * @param sysMenu
     * @return
     */
    void insert(SysMenu sysMenu);

    /**
     * 批量插入
     * @param sysLogs
     * @return
     */
    void insertBatch(List<SysMenu> sysLogs);

    /**
     * 设置为无效
     * @param id
     * @return
     */
    public void invalid(String id);

    /**
     * 批量设置为无效
     * @param ids
     * @return
     */
    void invalidBatch(String[] ids);

    /**
     * 删除
     * @param id
     * @return
     */
    void delete(String id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    void deleteBatch(String[] ids);


    /**
     * 根据角色id获取menu
     * @param roleId
     * @return
     */
    List<SysMenu> findAllMenusByRoleId(String roleId);

    /**
     * 根据角色id获取menu
     * @param roleId
     * @return
     */
    List<SysMenu> findAllMenuTreeByRoleId(String roleId);

    /**
     * 校验权限，直接获取当前用户的所有permission
     * @return
     */
    List<SysMenu> findAllMenusByUserId(String userId);

}

