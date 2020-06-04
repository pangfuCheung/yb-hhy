package com.yb.cheung.modules.bss.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yb.cheung.common.annotation.KeyWord;
import com.yb.cheung.common.annotation.validate.NotEmpty;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 业务渠道信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
@Data
@TableName("bss_channel")
public class BssChannel extends BaseEntity {

	/**
	 * 渠道编码
	 */
	private String channelCode;

	/**
	 * 是否投放 0：不投放 1投放
	 */
	private Integer releaseCode;

	/**
	 * 渠道ID 
	 */
	@KeyWord
	private String adId;
	
	/**
	 * 渠道名称 
	 */
	@KeyWord
	private String adName;

	/**
	 * 投放人ID 
	 */
	private String opId;
	
	/**
	 * 投放人名称 
	 */
	@KeyWord
	private String opName;
	
	/**
	 * 投放时间 
	 */
	private Date releaseTime;

	@TableField(exist = false)
	private Date[] releaseTimeValue;

	/**
	 * 品牌备注 
	 */
	private String remark;

	/**
	 * 微信号
	 */
	@TableField(exist = false)
	private String wechatCode;

	/**
	 * 品牌ID
	 */
	@TableField(exist = false)
	private String brandId;

	@NotEmpty(message = "用户账号不能为空")
	@TableField(exist = false)
	private String account;

	/**
	 * 品牌名称
	 */
	@TableField(exist = false)
	private String brandName;

	@TableField(exist = false)
	private List<BssWechat> bssWechatList;

	@TableField(exist = false)
	private List<BssChannelStatistics> bssChannelStatistics;

}
