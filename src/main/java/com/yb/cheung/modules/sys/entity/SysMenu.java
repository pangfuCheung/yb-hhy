package com.yb.cheung.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yb.cheung.common.annotation.KeyWord;
import com.yb.cheung.common.annotation.QueryCondition;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 系统菜单信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 23:45:39
 */
@Data
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

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
	 * 菜单url集合 
	 */
	@KeyWord
	private String url;

	/**
	 * 按钮权限集合 
	 */
	@KeyWord
	private String perms;
	
	/**
	 * 图标 
	 */
	private String icon;
	
	/**
	 * 类型 1：菜单 2：权限 
	 */
	private String type;
	
	/**
	 * 排序序号
	 */
	private Integer orderNum;

	@TableField(exist=false)
	private List<SysMenu> childrens;
	
}
