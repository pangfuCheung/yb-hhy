package com.yb.cheung.modules.bss.dao;

import com.yb.cheung.modules.bss.entity.BssBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 业务品牌信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
@Mapper
public interface BssBrandDao extends BaseMapper<BssBrand> {

    /**
     * 根据chanel中间表查询品牌
     * @param channelId
     * @return
     */
    BssBrand queryByChannelId(String channelId);
	
}
