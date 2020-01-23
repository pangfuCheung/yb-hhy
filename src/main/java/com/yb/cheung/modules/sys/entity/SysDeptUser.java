package com.yb.cheung.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 系统角色用户中间表
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 23:45:39
 */
@Data
@TableName("sys_dept_user")
public class SysDeptUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id 
	 */
	@TableId
	private String uuid;

	/**
	 * 部门id 
	 */
	private String deptId;
	
	/**
	 * 用户id 
	 */
	private String userId;
	
}
