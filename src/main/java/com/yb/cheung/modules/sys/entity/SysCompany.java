package com.yb.cheung.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.yb.cheung.common.annotation.KeyWord;
import com.yb.cheung.common.annotation.QueryCondition;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

/**
 * 系统公司信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-01 23:45:39
 */
@Data
@TableName("sys_company")
public class SysCompany {
	@TableId
	private String uuid;

	/**
	 * 父id 
	 */
	private String parentId;
	
	/**
	 * 公司名称 
	 */
	@KeyWord
	private String name;
	
	/**
	 * 公司编码 
	 */
	@KeyWord
	private String code;
	
	/**
	 * 公司地址 
	 */
	@KeyWord
	private String adress;
	
	/**
	 * 备注 
	 */
	private String remark;

	/**
	 * 状态 -1:删除 0:无效 1:有效
	 */
	private Integer status;

	/**
	 * 创建人id
	 */
	private String creatorId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 接收前端临时字段
	 */
	@TableField(exist = false)
	private Date[] createTimeValue;

	/**
	 * 更新人id
	 */
	private String operatorId;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 接收前端临时字段
	 */
	@TableField(exist = false)
	private Date[] updateTimeValue;
}
