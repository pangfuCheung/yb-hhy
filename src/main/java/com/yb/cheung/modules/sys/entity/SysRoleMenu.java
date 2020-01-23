package com.yb.cheung.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 系统角色菜单中间表
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 23:45:39
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private String uuid;
	
	/**
	 * 角色id 
	 */
	private String roleId;
	
	/**
	 * 菜单id 
	 */
	private String menuId;
	
}
