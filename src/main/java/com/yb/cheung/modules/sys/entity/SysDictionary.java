package com.yb.cheung.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2020-01-03 14:32:35
 */
@Data
@TableName("sys_dictionary")
public class SysDictionary extends BaseEntity {


	/**
	 * uuid
	 */
	
	/**
	 * 父id
	 */
	private String pid;
	
	/**
	 * 字典的code
	 */
	private String code;
	
	/**
	 * 字典的值
	 */
	private String value;
	
	/**
	 * 排序编号
	 */
	private Integer sortNo;

	/**
	 * 备注
	 */
	private String remark;
	
}
