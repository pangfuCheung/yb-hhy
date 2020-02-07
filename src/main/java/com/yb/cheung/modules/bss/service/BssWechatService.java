package com.yb.cheung.modules.bss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.modules.bss.entity.BssWechat;

import java.util.List;
import java.util.Map;

/**
 * 业务微信信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
public interface BssWechatService extends IService<BssWechat> {

    PageUtils queryPage(Map<String, Object> params);

    List<BssWechat> listByParams(Map<String,Object> params);


    /**
     * 更新或者保存
     * @param bssWechat
     * @return
     */
    void update(BssWechat bssWechat);

    /**
     * 批量保存
     * @param sysLogs
     * @return
     */
    void updateBatch(List<BssWechat> sysLogs);

    /**
     * 插入数据
     * @param bssWechat
     * @return
     */
    void insert(BssWechat bssWechat);

    /**
     * 批量插入
     * @param sysLogs
     * @return
     */
    void insertBatch(List<BssWechat> sysLogs);

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
     * 根据品牌查询微信号
     * @param brandId
     * @return
     */
    List<BssWechat> listByBrandId(String brandId);

    /**
     * 根据渠道中间表查询微信列表
     * @param channelId
     * @return
     */
    List<BssWechat> listByChannelId(String channelId);



}

