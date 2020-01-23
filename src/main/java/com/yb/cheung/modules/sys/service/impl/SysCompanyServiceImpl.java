package com.yb.cheung.modules.sys.service.impl;

import com.yb.cheung.common.utils.PW;
import com.yb.cheung.common.utils.QW;
import com.yb.cheung.modules.sys.entity.SysDept;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.Query;

import com.yb.cheung.modules.sys.dao.SysCompanyDao;
import com.yb.cheung.modules.sys.entity.SysCompany;
import com.yb.cheung.modules.sys.service.SysCompanyService;



@Service("sysCompanyService")
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyDao, SysCompany> implements SysCompanyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysCompany> page = this.page(
                new Query<SysCompany>().getPage(params),
                QW.getQW(params,SysCompany.class,true)
        );

        return new PageUtils(page);
    }

    @Override
    public void insert(SysCompany sysCompany) {
        save(sysCompany);
    }

    @Override
    public void insertBatch(List<SysCompany> sysCompanys) {
        saveBatch(sysCompanys);
    }

    @Override
    public void update(SysCompany sysCompany) {
        updateById(sysCompany);
    }

    @Override
    public void updateBatch(List<SysCompany> sysCompanys) {
        updateBatchById(sysCompanys);
    }

    @Override
    public void invalid(String id) {
        SysCompany SysCompany = getById(id);
        SysCompany.setStatus(0);
        update(SysCompany);
    }

    @Override
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            SysCompany SysCompany = getById(id);
            SysCompany.setStatus(0);
            update(SysCompany);
        }
    }

    @Override
    public void delete(String id) {
        SysCompany SysCompany = getById(id);
        SysCompany.setStatus(-1);
        update(SysCompany);
    }

    @Override
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            SysCompany SysCompany = getById(id);
            SysCompany.setStatus(-1);
            update(SysCompany);
        }
    }

}