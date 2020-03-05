package com.yb.cheung.modules.bss.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.yb.cheung.common.annotation.KeyWord;
import lombok.Data;
import com.yb.cheung.common.base.BaseEntity;

/**
 * 业务微信信息
 * 
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-02 00:33:59
 */
@Data
@TableName("bss_wechat")
public class BssWechat extends BaseEntity {

	/**
	 * 父id 
	 */
	@ExcelIgnore
	private String parentId;
	
	/**
	 * 微信号 
	 */
	@ExcelProperty(value="微信号",index=0)
	@KeyWord
	private String wechatCode;
	
	/**
	 * 客服名称 
	 */
	@ExcelProperty(value="客服名称",index=1)
	@KeyWord
	private String customName;
	
	/**
	 * 手机号 
	 */
	@ExcelProperty(value="手机号",index=2)
	@KeyWord
	private String phoneNumber;
	
	/**
	 * QQ号 
	 */
	@ExcelProperty(value="QQ号",index=3)
	@KeyWord
	private String qqNumber;

	/**
	 * 好友上限 
	 */
	@ExcelProperty(value="好友上限",index=4)
	private Integer friendLimit;
	
	/**
	 * 二维码URL 
	 */
	@ExcelProperty(value="二维码URL",index=5)
	private String qrCodeUrl;
	
	/**
	 * 下线原因 
	 */
	@ExcelProperty(value="下线原因",index=6)
	@KeyWord
	private String offlineReason;

	/**
	 * 品牌id
	 */
	@ExcelIgnore
	@TableField(exist = false)
	private String bssBrandId;

	/**
	 * 品牌名称
	 */
	@ExcelIgnore
	@TableField(exist = false)
	private String bssBrandName;

}
