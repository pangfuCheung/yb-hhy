package com.yb.cheung.modules.bss.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.cheung.modules.bss.annotation.BssMethodLog;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.Query;

import com.yb.cheung.modules.bss.dao.BssChannelStatisticsDao;
import com.yb.cheung.modules.bss.entity.BssChannelStatistics;
import com.yb.cheung.modules.bss.service.BssChannelStatisticsService;



@Service("bssChannelStatisticsService")
public class BssChannelStatisticsServiceImpl extends ServiceImpl<BssChannelStatisticsDao, BssChannelStatistics> implements BssChannelStatisticsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BssChannelStatistics> page = this.page(
                new Query<BssChannelStatistics>().getPage(params),
                new QueryWrapper<BssChannelStatistics>()
        );

        return new PageUtils(page);
    }

    @Override
    @BssMethodLog(remark = "新增渠道微信号分析统计")
    public void insert(BssChannelStatistics bssChannelStatistics) {
        save(bssChannelStatistics);
    }

    @Override
    @BssMethodLog(remark = "批量新增渠道微信号分析统计")
    public void insertBatch(List<BssChannelStatistics> bssChannelStatisticss) {
        saveBatch(bssChannelStatisticss);
    }

    @Override
    @BssMethodLog(remark = "修改渠道微信号分析统计")
    public void update(BssChannelStatistics bssChannelStatistics) {
        updateById(bssChannelStatistics);
    }

    @Override
    @BssMethodLog(remark = "批量修改渠道微信号分析统计")
    public void updateBatch(List<BssChannelStatistics> bssChannelStatisticss) {
        updateBatchById(bssChannelStatisticss);
    }

    @Override
    @BssMethodLog(remark = "设置无效渠道微信号分析统计")
    public void invalid(String id) {
        BssChannelStatistics BssChannelStatistics = getById(id);
        BssChannelStatistics.setStatus(0);
        update(BssChannelStatistics);
    }

    @Override
    @BssMethodLog(remark = "批量设置无效渠道微信号分析统计")
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            BssChannelStatistics BssChannelStatistics = getById(id);
            BssChannelStatistics.setStatus(0);
            update(BssChannelStatistics);
        }
    }

    @Override
    @BssMethodLog(remark = "删除渠道微信号分析统计")
    public void delete(String id) {
        BssChannelStatistics BssChannelStatistics = getById(id);
        BssChannelStatistics.setStatus(-1);
        update(BssChannelStatistics);
    }

    @Override
    @BssMethodLog(remark = "批量删除渠道微信号分析统计")
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            BssChannelStatistics BssChannelStatistics = getById(id);
            BssChannelStatistics.setStatus(-1);
            update(BssChannelStatistics);
        }
    }

}