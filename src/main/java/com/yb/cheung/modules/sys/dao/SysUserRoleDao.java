package com.yb.cheung.modules.sys.dao;

import com.yb.cheung.modules.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户角色中间表
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 19:47:49
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {
	
}
