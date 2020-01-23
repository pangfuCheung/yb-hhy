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

import com.yb.cheung.modules.bss.dao.BssLogDao;
import com.yb.cheung.modules.bss.entity.BssLog;
import com.yb.cheung.modules.bss.service.BssLogService;



@Service("bssLogService")
public class BssLogServiceImpl extends ServiceImpl<BssLogDao, BssLog> implements BssLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BssLog> page = this.page(
                new Query<BssLog>().getPage(params),
                new QueryWrapper<BssLog>()
        );

        return new PageUtils(page);
    }

    @Override
    @BssMethodLog(remark = "新增业务操作日志信息")
    public void insert(BssLog bssLog) {
        save(bssLog);
    }

    @Override
    @BssMethodLog(remark = "批量新增业务操作日志信息")
    public void insertBatch(List<BssLog> bssLogs) {
        saveBatch(bssLogs);
    }

    @Override
    @BssMethodLog(remark = "修改业务操作日志信息")
    public void update(BssLog bssLog) {
        super.updateById(bssLog);
    }

    @Override
    @BssMethodLog(remark = "批量修改业务操作日志信息")
    public void updateBatch(List<BssLog> bssLogs) {
        super.updateBatchById(bssLogs);
    }

    @Override
    @BssMethodLog(remark = "设置无效业务操作日志信息")
    public void invalid(String id) {
        BssLog BssLog = getById(id);
        BssLog.setStatus(0);
        update(BssLog);
    }

    @Override
    @BssMethodLog(remark = "批量设置无效业务操作日志信息")
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            BssLog BssLog = getById(id);
            BssLog.setStatus(0);
            update(BssLog);
        }
    }

    @Override
    @BssMethodLog(remark = "删除业务操作日志信息")
    public void delete(String id) {
        BssLog BssLog = getById(id);
        BssLog.setStatus(-1);
        update(BssLog);
    }

    @Override
    @BssMethodLog(remark = "批量删除业务操作日志信息")
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            BssLog BssLog = getById(id);
            BssLog.setStatus(-1);
            update(BssLog);
        }
    }

}