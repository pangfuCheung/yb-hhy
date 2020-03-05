package com.yb.cheung.modules.bss.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.yb.cheung.common.annotation.KeyWord;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 业务操作日志信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
@Data
@TableName("bss_log")
public class BssLog extends BaseEntity {

	/**
	 * 操作人id 
	 */
	private String opId;
	
	/**
	 * 操作人名称 
	 */
	@KeyWord
	private String opName;
	
	/**
	 * 操作内容 
	 */
	@KeyWord
	private String content;
	
}
