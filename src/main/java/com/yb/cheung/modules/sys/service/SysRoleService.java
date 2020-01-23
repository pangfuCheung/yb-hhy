package com.yb.cheung.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.modules.sys.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * 系统角色信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 19:47:50
 */
public interface SysRoleService extends IService<SysRole> {

    boolean isAdmin(String userId);

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 更新或者保存
     * @param sysRole
     * @return
     */
    void update(SysRole sysRole);

    /**
     * 批量保存
     * @param sysLogs
     * @return
     */
    void updateBatch(List<SysRole> sysLogs);

    /**
     * 插入数据
     * @param sysRole
     * @return
     */
    void insert(SysRole sysRole);

    /**
     * 批量插入
     * @param sysLogs
     * @return
     */
    void insertBatch(List<SysRole> sysLogs);

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
     * 给角色分配权限
     * @param sysRole
     */
    void allotMenus(SysRole sysRole);
}

