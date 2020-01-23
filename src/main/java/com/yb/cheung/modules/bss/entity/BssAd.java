package com.yb.cheung.modules.bss.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 业务广告信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
@Data
@TableName("bss_ad")
public class BssAd extends BaseEntity {

	/**
	 * 广告名称 
	 */
	private String name;
	
	/**
	 * 广告备注 
	 */
	private String remark;
	
}
