package com.yb.cheung.modules.bss.service.impl;

import com.yb.cheung.common.utils.QW;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.cheung.modules.bss.annotation.BssMethodLog;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.Query;

import com.yb.cheung.modules.bss.dao.BssAdDao;
import com.yb.cheung.modules.bss.entity.BssAd;
import com.yb.cheung.modules.bss.service.BssAdService;



@Service("bssAdService")
public class BssAdServiceImpl extends ServiceImpl<BssAdDao, BssAd> implements BssAdService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BssAd> page = this.page(
                new Query<BssAd>().getPage(params),
                QW.getQW(params,BssAd.class,true)
        );

        return new PageUtils(page);
    }

    @Override
    @BssMethodLog(remark = "新增业务广告信息")
    public void insert(BssAd bssAd) {
        save(bssAd);
    }

    @Override
    @BssMethodLog(remark = "批量新增业务广告信息")
    public void insertBatch(List<BssAd> bssAds) {
        saveBatch(bssAds);
    }

    @Override
    @BssMethodLog(remark = "修改业务广告信息")
    public void update(BssAd bssAd) {
        super.updateById(bssAd);
    }

    @Override
    @BssMethodLog(remark = "批量修改业务广告信息")
    public void updateBatch(List<BssAd> bssAds) {
        super.saveBatch(bssAds);
    }

    @Override
    @BssMethodLog(remark = "设置无效业务广告信息")
    public void invalid(String id) {
        BssAd BssAd = getById(id);
        BssAd.setStatus(0);
        update(BssAd);
    }

    @Override
    @BssMethodLog(remark = "批量设置无效业务广告信息")
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            BssAd BssAd = getById(id);
            BssAd.setStatus(0);
            update(BssAd);
        }
    }

    @Override
    @BssMethodLog(remark = "删除业务广告信息")
    public void delete(String id) {
        BssAd BssAd = getById(id);
        BssAd.setStatus(-1);
        update(BssAd);
    }

    @Override
    @BssMethodLog(remark = "批量删除业务广告信息")
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            BssAd BssAd = getById(id);
            BssAd.setStatus(-1);
            update(BssAd);
        }
    }

}