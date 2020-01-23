package com.yb.cheung.modules.sys.dao;

import com.yb.cheung.modules.sys.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统部门组织信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 19:47:50
 */
@Mapper
public interface SysDeptDao extends BaseMapper<SysDept> {

    @Select(" select * from where 1=1  ")
    List<SysDept> findAllDepts();
	
}
