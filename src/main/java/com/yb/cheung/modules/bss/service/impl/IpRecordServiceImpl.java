package com.yb.cheung.modules.bss.service.impl;

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

import com.yb.cheung.common.utils.QW;
import com.yb.cheung.modules.bss.dao.IpRecordDao;
import com.yb.cheung.modules.bss.entity.IpRecord;
import com.yb.cheung.modules.bss.service.IpRecordService;

@Service("ipRecordService")
public class IpRecordServiceImpl extends ServiceImpl<IpRecordDao, IpRecord> implements IpRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<IpRecord> page = this.page(
                new Query<IpRecord>().getPage(params),
                QW.getQW(params,IpRecord.class,true)
        );

        return new PageUtils(page);
    }

    @Override
    @BssMethodLog(remark = "新增IP访问记录")
    public void insert(IpRecord ipRecord) {
        save(ipRecord);
    }

    @Override
    @BssMethodLog(remark = "批量新增IP访问记录")
    public void insertBatch(List<IpRecord> ipRecords) {
        saveBatch(ipRecords);
    }

    @Override
    @BssMethodLog(remark = "修改IP访问记录")
    public void update(IpRecord ipRecord) {
        super.updateById(ipRecord);
    }

    @Override
    @BssMethodLog(remark = "批量修改IP访问记录")
    public void updateBatch(List<IpRecord> ipRecords) {
        super.saveBatch(ipRecords);
    }

    @Override
    @BssMethodLog(remark = "设置无效IP访问记录")
    public void invalid(String uuid) {
        IpRecord ipRecord = getById(uuid);
        update(ipRecord);
    }

    @Override
    @BssMethodLog(remark = "批量设置无效IP访问记录")
    public void invalidBatch(String[] uuids) {
        for (String uuid:uuids){
            IpRecord ipRecord = getById(uuid);
            update(ipRecord);
        }
    }

    @Override
    @BssMethodLog(remark = "删除IP访问记录")
    public void delete(String uuid) {
        IpRecord ipRecord = getById(uuid);
        update(ipRecord);
    }

    @Override
    @BssMethodLog(remark = "批量删除IP访问记录")
    public void deleteBatch(String[] uuids) {
        for (String uuid:uuids){
            IpRecord ipRecord = getById(uuid);
            update(ipRecord);
        }
    }

}
