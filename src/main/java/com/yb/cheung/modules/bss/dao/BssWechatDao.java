package com.yb.cheung.modules.bss.dao;

import com.yb.cheung.modules.bss.entity.BssWechat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yb.cheung.modules.sys.entity.SysDictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 业务微信信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
@Mapper
public interface BssWechatDao extends BaseMapper<BssWechat> {

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
