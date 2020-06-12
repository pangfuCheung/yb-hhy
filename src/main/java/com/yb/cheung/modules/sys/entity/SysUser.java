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
 * 系统用户信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 23:45:39
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

	/**
	 * 账号 
	 */
	@KeyWord
	private String account;
	
	/**
	 * 用户名 
	 */
	@KeyWord
	private String username;
	
	/**
	 * 密码 
	 */
	private String password;
	
	/**
	 * 邮箱 
	 */
	@KeyWord
	private String email;
	
	/**
	 * 手机号 
	 */
	@KeyWord
	private String phone;
	
	/**
	 * 地址 
	 */
	@KeyWord
	private String adress;
	
	/**
	 * 盐值 
	 */
	private String salt;

	/**
	 * 是否为管理员用户
	 */
	private String isAdmin;

	@TableField(exist = false)
	private String[] roleIds;
	
}
