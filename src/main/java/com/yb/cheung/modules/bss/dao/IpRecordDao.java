package com.yb.cheung.modules.bss.dao;

import com.yb.cheung.modules.bss.entity.IpRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * IP访问记录
 * 
 * @author pangfu
 * @email pangfucheung@163.com
 * @date 2020-03-09 09:50:26
 */
@Mapper
public interface IpRecordDao extends BaseMapper<IpRecord> {
	
}
