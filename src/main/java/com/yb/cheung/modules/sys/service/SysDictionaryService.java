package com.yb.cheung.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.modules.sys.entity.SysDictionary;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2020-01-03 14:32:35
 */
public interface SysDictionaryService extends IService<SysDictionary> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 更新或者保存
     * @param sysDictionary
     * @return
     */
    void update(SysDictionary sysDictionary);

    /**
     * 批量保存
     * @param sysLogs
     * @return
     */
    void updateBatch(List<SysDictionary> sysLogs);

    /**
     * 插入数据
     * @param sysDictionary
     * @return
     */
    void insert(SysDictionary sysDictionary);

    /**
     * 批量插入
     * @param sysLogs
     * @return
     */
    void insertBatch(List<SysDictionary> sysLogs);

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

