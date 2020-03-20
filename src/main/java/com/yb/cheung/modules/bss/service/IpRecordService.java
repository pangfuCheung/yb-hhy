package com.yb.cheung.modules.bss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.modules.bss.entity.IpRecord;

import java.util.List;
import java.util.Map;

/**
 * IP访问记录
 *
 * @author pangfu
 * @email pangfucheung@163.com
 * @date 2020-03-09 09:50:26
 */
public interface IpRecordService extends IService<IpRecord> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 更新或者保存
     * @param ipRecord
     * @return
     */
    void update(IpRecord ipRecord);

    /**
     * 批量保存
     * @param ipRecords
     * @return
     */
    void updateBatch(List<IpRecord> ipRecords);

    /**
     * 插入数据
     * @param ipRecord
     * @return
     */
    void insert(IpRecord ipRecord);

    /**
     * 批量插入
     * @param ipRecords
     * @return
     */
    void insertBatch(List<IpRecord> ipRecords);

    /**
     * 设置为无效
     * @param uuid
     * @return
     */
    public void invalid(String uuid);

    /**
     * 批量设置为无效
     * @param uuids
     * @return
     */
    void invalidBatch(String[] uuids);

    /**
     * 删除
     * @param uuid
     * @return
     */
    void delete(String uuid);

    /**
     * 批量删除
     * @param uuids
     * @return
     */
    void deleteBatch(String[] uuids);

}

