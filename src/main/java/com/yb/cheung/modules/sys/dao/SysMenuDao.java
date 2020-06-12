package com.yb.cheung.modules.sys.dao;

import com.yb.cheung.modules.sys.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统菜单信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 19:47:50
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenu> {

    @Select("SELECT sm.* " +
            "FROM sys_menu sm " +
            "LEFT JOIN sys_role_menu srm ON sm.uuid = srm.menu_id " +
            "LEFT JOIN sys_role sr ON sr.uuid = srm.menu_id " +
            "LEFT JOIN sys_user_role sur ON sur.role_id = sr.uuid " +
            "LEFT JOIN ( SELECT * FROM sys_user WHERE uuid = #{userId} ) su ON su.uuid = sur.user_id " +
            "where sm.uuid IS NOT NULL " +
            "GROUP BY sm.uuid ORDER BY sm.create_time ASC ")
    List<SysMenu> findAllMenuChildrens(String userId);

    @Select("SELECT sm.* FROM sys_menu sm LEFT JOIN sys_role_menu srm ON sm.uuid = srm.menu_id WHERE srm.role_id = #{roleId} and sm.uuid IS NOT NULL ")
    List<SysMenu> findAllMenusByRoleId(String roleId);

    List<SysMenu> findAllMenusByCurrentUser(String userId);

}
