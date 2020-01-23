package com.yb.cheung.modules.bss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.modules.bss.entity.BssBrand;

import java.util.List;
import java.util.Map;

/**
 * 业务品牌信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
public interface BssBrandService extends IService<BssBrand> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 更新或者保存
     * @param bssBrand
     * @return
     */
    void update(BssBrand bssBrand);

    /**
     * 批量保存
     * @param sysLogs
     * @return
     */
    void updateBatch(List<BssBrand> sysLogs);

    /**
     * 插入数据
     * @param bssBrand
     * @return
     */
    void insert(BssBrand bssBrand);

    /**
     * 批量插入
     * @param sysLogs
     * @return
     */
    void insertBatch(List<BssBrand> sysLogs);

    /**
     * 设置为无效
     * @param id
     * @return
     */
    public void invalid(String id);

    /**
     * 批量设置为无效
     * @param ids
     * @return
     */
    void invalidBatch(String[] ids);

    /**
     * 删除
     * @param id
     * @return
     */
    void delete(String id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    void deleteBatch(String[] ids);

    /**
     * 根据chanel中间表查询品牌
     * @param channelId
     * @return
     */
    BssBrand queryByChannelId(String channelId);
    
}

