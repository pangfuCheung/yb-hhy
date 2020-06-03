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
 * @date 2020-01-06 15:12:40
 */
@Data
@TableName("bss_channel_wechat")
public class BssChannelWechat {

	@TableId
	private String uuid;
	
	private String channelId;
	
	private String wechatId;

	private Integer weight;

	public BssChannelWechat(){

	}

	public BssChannelWechat(String channelId,String wechatId){
		this.channelId = channelId;
		this.wechatId = wechatId;
	}

	
}
