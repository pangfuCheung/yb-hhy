package com.yb.cheung.modules.bss.dao;

import com.yb.cheung.modules.bss.entity.BssLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 业务操作日志信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
@Mapper
public interface BssLogDao extends BaseMapper<BssLog> {
	
}
