package com.yb.cheung.modules.bss.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 渠道微信号分析统计
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2020-01-07 17:07:01
 */
@Data
@TableName("bss_channel_statistics")
public class BssChannelStatistics extends BaseEntity {

	/**
	 * 渠道id
	 */
	private String channelId;
	
	/**
	 * 微信id
	 */
	private String wechatId;
	
	/**
	 * 微信号
	 */
	private String wechatCode;
	
	/**
	 * 品牌id
	 */
	private String brandId;
	
	/**
	 * 好友数量
	 */
	private Integer friendNumber;
	
	/**
	 * 加载次数
	 */
	private Integer loadNumber;
	
	/**
	 * 滑动长度
	 */
	private Integer slideLength;
	
	/**
	 * 长按次数
	 */
	private Integer pressNumber;
	
	/**
	 * 复制次数
	 */
	private Integer copyNumber;
	
	/**
	 * 扫二维码次数
	 */
	private Integer qrcodeNumber;
	
	/**
	 * 点击次数
	 */
	private Integer clickNumber;
	
	/**
	 * 跳转微信次数
	 */
	private Integer skipWechatNumber;
	
	/**
	 * 轮播次数
	 */
	private Integer showNumber;
	
	/**
	 * 权重占比
	 */
	private Double weightRate;
	
	/**
	 * 统计类型
	 */
	private Integer type;

	/**
	 * 用户ip
	 */
	@TableField(exist = false)
	private String ipAdress;

	/**
	 * 渠道编码
	 */
	@TableField(exist = false)
	private String channelCode;

}
