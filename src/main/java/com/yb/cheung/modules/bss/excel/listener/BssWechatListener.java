package com.yb.cheung.modules.bss.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.yb.cheung.modules.bss.entity.BssWechat;
import com.yb.cheung.modules.bss.service.BssWechatService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BssWechatListener extends AnalysisEventListener<BssWechat> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    private List<BssWechat> list = new ArrayList<BssWechat>();

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private BssWechatService bssWechatService;

    public BssWechatListener(BssWechatService bssWechatService){
        this.bssWechatService = bssWechatService;
    }

    @Override
    public void invoke(BssWechat bssWechat, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(bssWechat));
        list.add(bssWechat);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        bssWechatService.saveBatch(list);
        log.info("存储数据库成功！");
    }
}
