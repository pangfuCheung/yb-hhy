package com.yb.cheung.modules.bss.service.impl;

import com.yb.cheung.common.utils.QW;
import com.yb.cheung.modules.bss.entity.BssBrand;
import com.yb.cheung.modules.bss.entity.BssWechatBrand;
import com.yb.cheung.modules.bss.service.BssBrandService;
import com.yb.cheung.modules.bss.service.BssWechatBrandService;
import org.apache.commons.lang.StringUtils;
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

import com.yb.cheung.modules.bss.dao.BssWechatDao;
import com.yb.cheung.modules.bss.entity.BssWechat;
import com.yb.cheung.modules.bss.service.BssWechatService;



@Service("bssWechatService")
public class BssWechatServiceImpl extends ServiceImpl<BssWechatDao, BssWechat> implements BssWechatService {

    @Autowired
    private BssWechatBrandService bssWechatBrandService;

    @Autowired
    private BssBrandService bssBrandService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BssWechat> page = this.page(
                new Query<BssWechat>().getPage(params),
                QW.getQW(params,BssWechat.class,true)
        );

        return new PageUtils(page);
    }

    @Override
    public List<BssWechat> listByParams(Map<String, Object> params) {
        return this.baseMapper.selectList(QW.getQW(params,BssWechat.class));
    }

    @Override
    @BssMethodLog(remark = "新增业务微信信息")
    public void insert(BssWechat bssWechat) {
        save(bssWechat);
        addBrand(bssWechat);
    }

    @Override
    @BssMethodLog(remark = "批量新增业务微信信息")
    public void insertBatch(List<BssWechat> bssWechats) {
        saveBatch(bssWechats);
        for (BssWechat bssWechat:bssWechats){
            addBrand(bssWechat);
        }
    }

    private void addBrand(BssWechat bssWechat){
        if (StringUtils.isNotEmpty(bssWechat.getBssBrandId())){
            bssWechatBrandService.save(new BssWechatBrand(bssWechat.getBssBrandId(),bssWechat.getUuid()));
        }
        // 兼容导入
        if (StringUtils.isNotEmpty(bssWechat.getBssBrandName())){
            Map<String,Object> params = new HashMap<>();
            params.put("name",bssWechat.getBssBrandName());
            BssBrand bssBrand = bssBrandService.getOne(QW.getQW(params, BssBrand.class));
            bssWechatBrandService.save(new BssWechatBrand(bssBrand.getUuid(),bssWechat.getUuid()));
        }
    }

    @Override
    @BssMethodLog(remark = "修改业务微信信息")
    public void update(BssWechat bssWechat) {
        super.updateById(bssWechat);
        if (StringUtils.isNotEmpty(bssWechat.getBssBrandId())){
            deleteBssWechatBrand(bssWechat);
            bssWechatBrandService.save(new BssWechatBrand(bssWechat.getBssBrandId(),bssWechat.getUuid()));
        }
    }

    @Override
    @BssMethodLog(remark = "批量修改业务微信信息")
    public void updateBatch(List<BssWechat> bssWechats) {
        super.updateBatchById(bssWechats);
        for (BssWechat bssWechat:bssWechats){
            if (StringUtils.isNotEmpty(bssWechat.getBssBrandId())){
                deleteBssWechatBrand(bssWechat);
                bssWechatBrandService.save(new BssWechatBrand(bssWechat.getBssBrandId(),bssWechat.getUuid()));
            }
        }
    }

    /**
     * 删除中间表
     * @param bssWechat
     */
    private void deleteBssWechatBrand(BssWechat bssWechat){
        Map<String,Object> params = new HashMap<>();
        params.put("wechatId",bssWechat.getUuid());
        List<BssWechatBrand> bssWechatBrands = bssWechatBrandService.list(QW.getQW(params,BssWechatBrand.class));
        for (BssWechatBrand bssWechatBrand:bssWechatBrands){
            bssWechatBrandService.removeById(bssWechatBrand.getUuid());
        }
    }

    @Override
    @BssMethodLog(remark = "设置无效业务微信信息")
    public void invalid(String id) {
        BssWechat BssWechat = getById(id);
        BssWechat.setStatus(0);
        update(BssWechat);
    }

    @Override
    @BssMethodLog(remark = "批量设置无效业务微信信息")
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            BssWechat BssWechat = getById(id);
            BssWechat.setStatus(0);
            update(BssWechat);
        }
    }

    @Override
    @BssMethodLog(remark = "删除业务微信信息")
    public void delete(String id) {
        BssWechat BssWechat = getById(id);
        BssWechat.setStatus(-1);
        update(BssWechat);
    }

    @Override
    @BssMethodLog(remark = "批量删除业务微信信息")
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            BssWechat BssWechat = getById(id);
            BssWechat.setStatus(-1);
            update(BssWechat);
        }
    }

    @Override
    @BssMethodLog(remark = "根据品牌查询微信号")
    public List<BssWechat> listByBrandId(String brandId){
        return baseMapper.listByBrandId(brandId);
    }

    @Override
    @BssMethodLog(remark = "根据渠道中间表查询微信号")
    public List<BssWechat> listByChannelId(String channelId) {
        return baseMapper.listByChannelId(channelId);
    }
}