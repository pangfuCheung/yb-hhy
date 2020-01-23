package com.yb.cheung.modules.sys.service.impl;

import com.yb.cheung.common.utils.*;
import com.yb.cheung.modules.sys.entity.SysCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yb.cheung.modules.sys.dao.SysDeptDao;
import com.yb.cheung.modules.sys.entity.SysDept;
import com.yb.cheung.modules.sys.service.SysDeptService;



@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDept> implements SysDeptService {

    @Override
    public List<SysDept> findSysDeptByNav(Map<String, Object> params) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("status", Constant.orStatus);
        List<SysDept> allDepts = super.list(queryWrapper);
        List<SysDept> parantDepts = new ArrayList<>();
        for (SysDept sysDept:allDepts){
            if (null != sysDept && null == sysDept.getParentId()){
                parantDepts.add(sysDept);
            }
        }
        for (SysDept sysDept:parantDepts){
            setSysDeptChildrens(sysDept,allDepts);
        }
        return parantDepts;
    }

    private void setSysDeptChildrens(SysDept parentDept,List<SysDept> allDepts){
        List<SysDept> chlids = new ArrayList<>();
        for (SysDept dept:allDepts){
            if (null != dept && parentDept.getUuid().equals(dept.getParentId())){
                chlids.add(dept);
            }
        }

        if (chlids.size() > 0){
            parentDept.setChildrens(chlids);
            for (SysDept sysDept:chlids){
                setSysDeptChildrens(sysDept,allDepts);
            }
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDept> page = this.page(
                new Query<SysDept>().getPage(params),
                QW.getQW(params, SysDept.class,true)
        );
        return new PageUtils(page);
    }

    @Override
    public void insert(SysDept sysDept) {
        save(sysDept);
    }

    @Override
    public void insertBatch(List<SysDept> sysDepts) {
        saveBatch(sysDepts);
    }

    @Override
    public void update(SysDept sysDept) {
        updateById(sysDept);
    }

    @Override
    public void updateBatch(List<SysDept> sysDepts) {
        updateBatchById(sysDepts);
    }

    @Override
    public void invalid(String id) {
        SysDept SysDept = getById(id);
        SysDept.setStatus(0);
        update(SysDept);
    }

    @Override
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            SysDept SysDept = getById(id);
            SysDept.setStatus(0);
            update(SysDept);
        }
    }

    @Override
    public void delete(String id) {
        SysDept SysDept = getById(id);
        SysDept.setStatus(-1);
        update(SysDept);
    }

    @Override
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            SysDept SysDept = getById(id);
            SysDept.setStatus(-1);
            update(SysDept);
        }
    }

}