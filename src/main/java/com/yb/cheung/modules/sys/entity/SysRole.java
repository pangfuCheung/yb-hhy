package com.yb.cheung.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yb.cheung.common.annotation.KeyWord;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 系统角色信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 23:45:39
 */
@Data
@TableName("sys_role")
public class SysRole extends BaseEntity {

	/**
	 * 名称 
	 */
	@KeyWord
	private String name;
	
	/**
	 * 唯一编码 
	 */
	@KeyWord
	private String code;
	
	/**
	 * 父id 
	 */
	private String parentId;
	
	/**
	 * 备注 
	 */
	@KeyWord
	private String remark;
	
	@TableField(exist = false)
	private String[] menuIds;
}
