package com.yb.cheung.modules.bss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.modules.bss.entity.BssAd;

import java.util.List;
import java.util.Map;

/**
 * 业务广告信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
public interface BssAdService extends IService<BssAd> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 更新或者保存
     * @param bssAd
     * @return
     */
    void update(BssAd bssAd);

    /**
     * 批量保存
     * @param sysLogs
     * @return
     */
    void updateBatch(List<BssAd> sysLogs);

    /**
     * 插入数据
     * @param bssAd
     * @return
     */
    void insert(BssAd bssAd);

    /**
     * 批量插入
     * @param sysLogs
     * @return
     */
    void insertBatch(List<BssAd> sysLogs);

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

