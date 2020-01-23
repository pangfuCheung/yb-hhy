package com.yb.cheung.modules.bss.entity;

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
 * @date 2020-01-04 13:33:24
 */
@Data
@TableName("bss_wechat_brand")
public class BssWechatBrand {

	public BssWechatBrand(String brandId,String wechatId){
		this.brandId = brandId;
		this.wechatId = wechatId;
	}

	/**
	 * uuid
	 */
	@TableId
	private String uuid;
	
	/**
	 * 品牌id
	 */
	private String brandId;
	
	/**
	 * 微信id
	 */
	private String wechatId;
	
}
