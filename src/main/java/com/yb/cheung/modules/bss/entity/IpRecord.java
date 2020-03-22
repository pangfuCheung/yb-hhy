package com.yb.cheung.modules.bss.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yb.cheung.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * IP访问记录
 * 
 * @author pangfu
 * @email pangfucheung@163.com
 * @date 2020-03-09 09:50:26
 */
@Data
@TableName("ip_record")
public class IpRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	@ExcelIgnore
	private String uuid;

	/**
	 * 渠道编码
	 */
	private String channelCode;
	/**
	 * 微信号
	 */
	private String wechatCode;
	/**
	 * IP地址
	 */
	private String ipAdress;
	/**
	 * IP所属区域
	 */
	private String ipArea;

	/**
	 * 公司id
	 */
	private String companyId;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 接受前端字段
	 */
	@TableField(exist = false)
	private Date[] createTimeValue;

	/**
	 * 渠道id
	 */
	private String channelId;

	/**
	 * 微信id
	 */
	private String wechatId;

}
