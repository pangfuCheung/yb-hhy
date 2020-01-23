package com.yb.cheung.modules.sys.dao;

import com.yb.cheung.modules.sys.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统用户信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 19:47:50
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {

    @Select(" select * from sys_user where 1=1 and company_id = #{companyId}")
    List<SysUser> findUsersByCompanyId(String companyId);
	
}
