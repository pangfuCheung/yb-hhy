package com.yb.cheung.modules.bss.service.impl;

import com.yb.cheung.common.utils.QW;
import com.yb.cheung.modules.bss.dao.BssChannelWechatDao;
import com.yb.cheung.modules.bss.entity.*;
import com.yb.cheung.modules.bss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.cheung.modules.bss.annotation.BssMethodLog;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.Query;

import com.yb.cheung.modules.bss.dao.BssChannelDao;


@Service("bssChannelService")
public class BssChannelServiceImpl extends ServiceImpl<BssChannelDao, BssChannel> implements BssChannelService {

    @Autowired
    private BssChannelWechatService bssChannelWechatService;

    @Autowired
    private BssWechatService bssWechatService;

    @Autowired
    private BssChannelStatisticsService bssChannelStatisticsService;

    @Autowired
    private BssBrandService bssBrandService;

    @Autowired
    private BssChannelBrandService bssChannelBrandService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BssChannel> page = this.page(
                new Query<BssChannel>().getPage(params),
                QW.getQW(params,BssChannel.class,true)
        );

        List<BssChannel> bssChannels = page.getRecords();
        if (!bssChannels.isEmpty()){
            for (BssChannel bssChannel:bssChannels){
                String channelId = bssChannel.getUuid();
                bssChannel.setBssWechatList(bssWechatService.listByChannelId(channelId));
                BssBrand bssBrand = bssBrandService.queryByChannelId(channelId);
                if (null != bssBrand){
                    bssChannel.setBrandId(bssBrand.getUuid());
                    bssChannel.setBrandName(bssBrand.getName());
                }
                List<BssChannelStatistics> bssChannelStatistics = bssChannelStatisticsService.list(QW.getQW("channelId",channelId,BssChannelStatistics.class));
                bssChannel.setBssChannelStatistics(bssChannelStatistics);
            }
        }

        return new PageUtils(page);
    }

    @Override
    @BssMethodLog(remark = "新增业务渠道信息")
    public void insert(BssChannel bssChannel) {
        save(bssChannel);
        saveChannelWechat(bssChannel);
        saveChannelBrand(bssChannel);
    }

    private void saveChannelWechats(List<BssChannel> bssChannels){
        for (BssChannel bssChannel:bssChannels){
            saveChannelWechat(bssChannel);
        }
    }

    private void saveChannelWechat(BssChannel bssChannel){
        String wechatCodes[] = bssChannel.getWechatCode().split(",");
        for (String wechatCode:wechatCodes){
            BssWechat bssWechat = bssWechatService.getOne(QW.getQW("wechatCode",wechatCode, BssWechat.class));
            bssChannelWechatService.save(new BssChannelWechat(bssChannel.getUuid(),bssWechat.getUuid()));
        }
    }

    private void saveChannelBrands(List<BssChannel> bssChannels){
        for (BssChannel bssChannel:bssChannels){
            saveChannelBrand(bssChannel);
        }
    }

    private void saveChannelBrand(BssChannel bssChannel){
        bssChannelBrandService.save(new BssChannelBrand(bssChannel.getUuid(),bssChannel.getBrandId()));
    }

    @Override
    @BssMethodLog(remark = "批量新增业务渠道信息")
    public void insertBatch(List<BssChannel> bssChannels) {
        saveBatch(bssChannels);
        saveChannelWechats(bssChannels);
        saveChannelBrands(bssChannels);
    }

    @Override
    @BssMethodLog(remark = "修改业务渠道信息")
    public void update(BssChannel bssChannel) {
        String channelId = bssChannel.getUuid();
        List<BssWechat> bssWechatList = bssChannel.getBssWechatList();
        if (null !=bssWechatList && !bssWechatList.isEmpty()){
            for (BssWechat bssWechat:bssWechatList){
                Integer weight = bssWechat.getWeight();
                Map<String,Object> param = new HashMap<>();
                param.put("wechatId",bssWechat.getUuid());
                param.put("channelId",channelId);

                BssChannelWechat bssChannelWechat = bssChannelWechatService.getOne(QW.getQW(param,BssChannelWechat.class));
                bssChannelWechat.setWeight(weight);
                bssChannelWechatService.updateById(bssChannelWechat);
            }
        }

        super.updateById(bssChannel);
    }

    @Override
    @BssMethodLog(remark = "批量修改业务渠道信息")
    public void updateBatch(List<BssChannel> bssChannels) {
        super.updateBatchById(bssChannels);
    }

    @Override
    @BssMethodLog(remark = "设置无效业务渠道信息")
    public void invalid(String id) {
        BssChannel BssChannel = getById(id);
        BssChannel.setStatus(0);
        update(BssChannel);
    }

    @Override
    @BssMethodLog(remark = "批量设置无效业务渠道信息")
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            BssChannel BssChannel = getById(id);
            BssChannel.setStatus(0);
            update(BssChannel);
        }
    }

    @Override
    @BssMethodLog(remark = "删除业务渠道信息")
    public void delete(String id) {
        BssChannel BssChannel = getById(id);
        BssChannel.setStatus(-1);
        update(BssChannel);
    }

    @Override
    @BssMethodLog(remark = "批量删除业务渠道信息")
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            BssChannel BssChannel = getById(id);
            BssChannel.setStatus(-1);
            update(BssChannel);
        }
    }

}