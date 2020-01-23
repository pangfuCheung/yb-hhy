package com.yb.cheung.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.modules.sys.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 系统用户信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 19:47:50
 */
public interface SysUserService extends IService<SysUser> {

    PageUtils queryPage(Map<String, Object> params);

    List<SysUser> findUsersByCompanyId(String companyId);

    /**
     * 更新或者保存
     * @param sysUser
     * @return
     */
    void update(SysUser sysUser);

    /**
     * 批量保存
     * @param sysLogs
     * @return
     */
    void updateBatch(List<SysUser> sysLogs);

    /**
     * 插入数据
     * @param sysUser
     * @return
     */
    void insert(SysUser sysUser);

    /**
     * 批量插入
     * @param sysLogs
     * @return
     */
    void insertBatch(List<SysUser> sysLogs);

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
     * 给用户分配角色
     * @param sysUser
     */
    void allotRoles(SysUser sysUser);


    /**
     * 检查密码是否与原来一致
     * @param sysUser
     * @return
     */
    Boolean checkPwd(SysUser sysUser);

    /**
     * 修改密码
     * @param sysUser
     * @return
     */
    Boolean alterPwd(SysUser sysUser);
    
}

