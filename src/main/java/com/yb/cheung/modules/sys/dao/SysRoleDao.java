package com.yb.cheung.modules.sys.dao;

import com.yb.cheung.modules.sys.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统角色信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 19:47:50
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRole> {

    @Select(" select sr.* from sys_role sr left join sys_user_role sur on sr.uuid = sur.role_id " +
            "left join sys_user su on su.uuid = sur.user_id where su.uuid = #{userId} AND sr.`code` = \"admin\" ")
    List<SysRole> findSysRoleByUserId(String userId);

}
