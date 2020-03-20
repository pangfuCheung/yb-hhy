package com.yb.cheung.modules.bss.vo;

import com.yb.cheung.modules.bss.entity.BssChannelStatistics;
import lombok.Data;

@Data
public class BssChannelStatisticsVO {

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
     * ip
     */
    private String ip;



}
