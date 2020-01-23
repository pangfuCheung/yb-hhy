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

import com.yb.cheung.modules.bss.dao.BssBrandDao;
import com.yb.cheung.modules.bss.entity.BssBrand;
import com.yb.cheung.modules.bss.service.BssBrandService;



@Service("bssBrandService")
public class BssBrandServiceImpl extends ServiceImpl<BssBrandDao, BssBrand> implements BssBrandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BssBrand> page = this.page(
                new Query<BssBrand>().getPage(params),
                new QueryWrapper<BssBrand>()
        );

        return new PageUtils(page);
    }

    @Override
    @BssMethodLog(remark = "新增业务品牌信息")
    public void insert(BssBrand bssBrand) {
        save(bssBrand);
    }

    @Override
    @BssMethodLog(remark = "批量新增业务品牌信息")
    public void insertBatch(List<BssBrand> bssBrands) {
        saveBatch(bssBrands);
    }

    @Override
    @BssMethodLog(remark = "修改业务品牌信息")
    public void update(BssBrand bssBrand) {
        super.updateById(bssBrand);
    }

    @Override
    @BssMethodLog(remark = "批量修改业务品牌信息")
    public void updateBatch(List<BssBrand> bssBrands) {
        super.updateBatchById(bssBrands);
    }

    @Override
    @BssMethodLog(remark = "设置无效业务品牌信息")
    public void invalid(String id) {
        BssBrand BssBrand = getById(id);
        BssBrand.setStatus(0);
        update(BssBrand);
    }

    @Override
    @BssMethodLog(remark = "批量设置无效业务品牌信息")
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            BssBrand BssBrand = getById(id);
            BssBrand.setStatus(0);
            update(BssBrand);
        }
    }

    @Override
    @BssMethodLog(remark = "删除业务品牌信息")
    public void delete(String id) {
        BssBrand BssBrand = getById(id);
        BssBrand.setStatus(-1);
        update(BssBrand);
    }

    @Override
    @BssMethodLog(remark = "批量删除业务品牌信息")
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            BssBrand BssBrand = getById(id);
            BssBrand.setStatus(-1);
            update(BssBrand);
        }
    }

    @Override
    @BssMethodLog(remark = "根据渠道id获取品牌")
    public BssBrand queryByChannelId(String channelId) {
        return baseMapper.queryByChannelId(channelId);
    }
}