package com.yb.cheung.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 系统用户角色中间表
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 23:45:39
 */
@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private String uuid;
	
	/**
	 * 用户id 
	 */
	private String userId;
	
	/**
	 * 角色id 
	 */
	private String roleId;
	
}
