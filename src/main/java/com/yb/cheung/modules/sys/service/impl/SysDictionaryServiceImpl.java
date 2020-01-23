package com.yb.cheung.modules.sys.service.impl;

import com.yb.cheung.common.utils.QW;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.cheung.modules.bss.annotation.BssMethodLog;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.Query;

import com.yb.cheung.modules.sys.dao.SysDictionaryDao;
import com.yb.cheung.modules.sys.entity.SysDictionary;
import com.yb.cheung.modules.sys.service.SysDictionaryService;



@Service("sysDictionaryService")
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryDao, SysDictionary> implements SysDictionaryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDictionary> page = this.page(
                new Query<SysDictionary>().getPage(params),
                QW.getQW(params,SysDictionary.class)
        );

        return new PageUtils(page);
    }

    @Override
    @BssMethodLog(remark = "新增")
    public void insert(SysDictionary sysDictionary) {
        save(sysDictionary);
    }

    @Override
    @BssMethodLog(remark = "批量新增")
    public void insertBatch(List<SysDictionary> sysDictionarys) {
        saveBatch(sysDictionarys);
    }

    @Override
    @BssMethodLog(remark = "修改")
    public void update(SysDictionary sysDictionary) {
        updateById(sysDictionary);
    }

    @Override
    @BssMethodLog(remark = "批量修改")
    public void updateBatch(List<SysDictionary> sysDictionarys) {
        updateBatchById(sysDictionarys);
    }

    @Override
    @BssMethodLog(remark = "设置无效")
    public void invalid(String id) {
        SysDictionary SysDictionary = getById(id);
        SysDictionary.setStatus(0);
        update(SysDictionary);
    }

    @Override
    @BssMethodLog(remark = "批量设置无效")
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            SysDictionary SysDictionary = getById(id);
            SysDictionary.setStatus(0);
            update(SysDictionary);
        }
    }

    @Override
    @BssMethodLog(remark = "删除")
    public void delete(String id) {
        SysDictionary SysDictionary = getById(id);
        SysDictionary.setStatus(-1);
        update(SysDictionary);
    }

    @Override
    @BssMethodLog(remark = "批量删除")
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            SysDictionary SysDictionary = getById(id);
            SysDictionary.setStatus(-1);
            update(SysDictionary);
        }
    }

}