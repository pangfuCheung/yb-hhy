package com.yb.cheung.modules.bss.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.yb.cheung.common.annotation.KeyWord;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 业务品牌信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
@Data
@TableName("bss_brand")
public class BssBrand extends BaseEntity {

	/**
	 * 品牌名称 
	 */
	@KeyWord
	private String name;
	
	/**
	 * 品牌备注 
	 */
	@KeyWord
	private String remark;
	
}
