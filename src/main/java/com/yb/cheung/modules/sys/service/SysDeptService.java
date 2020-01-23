package com.yb.cheung.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.modules.sys.entity.SysDept;

import java.util.List;
import java.util.Map;

/**
 * 系统部门组织信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 19:47:50
 */
public interface SysDeptService extends IService<SysDept> {

    List<SysDept> findSysDeptByNav(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 更新或者保存
     * @param sysDept
     * @return
     */
    void update(SysDept sysDept);

    /**
     * 批量保存
     * @param sysLogs
     * @return
     */
    void updateBatch(List<SysDept> sysLogs);

    /**
     * 插入数据
     * @param sysDept
     * @return
     */
    void insert(SysDept sysDept);

    /**
     * 批量插入
     * @param sysLogs
     * @return
     */
    void insertBatch(List<SysDept> sysLogs);

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
    
}

