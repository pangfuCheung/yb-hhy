package com.yb.cheung.modules.bss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.modules.bss.entity.BssChannelStatistics;

import java.util.List;
import java.util.Map;

/**
 * 渠道微信号分析统计
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2020-01-07 17:07:01
 */
public interface BssChannelStatisticsService extends IService<BssChannelStatistics> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 更新或者保存
     * @param bssChannelStatistics
     * @return
     */
    void update(BssChannelStatistics bssChannelStatistics);

    /**
     * 批量保存
     * @param sysLogs
     * @return
     */
    void updateBatch(List<BssChannelStatistics> sysLogs);

    /**
     * 插入数据
     * @param bssChannelStatistics
     * @return
     */
    void insert(BssChannelStatistics bssChannelStatistics);

    /**
     * 批量插入
     * @param sysLogs
     * @return
     */
    void insertBatch(List<BssChannelStatistics> sysLogs);

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

