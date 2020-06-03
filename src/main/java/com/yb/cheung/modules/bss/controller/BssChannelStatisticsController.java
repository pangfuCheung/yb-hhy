package com.yb.cheung.modules.bss.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mchange.lang.IntegerUtils;
import com.yb.cheung.common.annotation.SysLog;
import com.yb.cheung.common.utils.IntegerUtil;
import com.yb.cheung.modules.bss.entity.BssChannel;
import com.yb.cheung.modules.bss.entity.IpRecord;
import com.yb.cheung.modules.bss.service.BssChannelService;
import com.yb.cheung.modules.bss.service.IpRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.yb.cheung.modules.bss.entity.BssChannelStatistics;
import com.yb.cheung.modules.bss.service.BssChannelStatisticsService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.annotation.YBRequestParam;

import javax.servlet.http.HttpServletRequest;


/**
 * 渠道微信号分析统计
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2020-01-07 17:07:01
 */
@Api(description = "渠道微信号分析统计")
@RestController
@RequestMapping("bss/bsschannelstatistics")
@Slf4j
public class BssChannelStatisticsController extends BaseController {
    @Autowired
    private BssChannelStatisticsService bssChannelStatisticsService;

    @Autowired
    private IpRecordService ipRecordService;

    @Autowired
    private BssChannelService bssChannelService;

    /**
     * 列表
     */
    @ApiOperation(value = "所有渠道微信号分析统计列表",httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = bssChannelStatisticsService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取渠道微信号分析统计",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid",value = "主键id" ,required = true , dataType = "String" ,paramType = "query")
    })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
		BssChannelStatistics bssChannelStatistics = bssChannelStatisticsService.getById(uuid);

        return R.ok().put("bssChannelStatistics", bssChannelStatistics);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存渠道微信号分析统计",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channel_id",value = "渠道id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "wechat_id",value = "微信id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "wechat_code",value = "微信号" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "brand_id",value = "品牌id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "friend_number",value = "好友数量" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "load_number",value = "加载次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "slide_length",value = "滑动长度" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "press_number",value = "长按次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "copy_number",value = "复制次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "qrcode_number",value = "扫二维码次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "click_number",value = "点击次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "skip_wechat_number",value = "跳转微信次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "show_number",value = "轮播次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "weight_rate",value = "权重占比" , dataType = "Double" ,paramType = "query"),
            @ApiImplicitParam(name = "type",value = "统计类型" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司名称" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建名称" , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间" , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人id" , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    @SysLog("保存渠道微信号分析统计")
    public R save(@YBRequestParam BssChannelStatistics bssChannelStatistics){
		bssChannelStatisticsService.insert(bssChannelStatistics);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改渠道微信号分析统计",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id" ,required = true , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "channel_id",value = "渠道id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "wechat_id",value = "微信id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "wechat_code",value = "微信号" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "brand_id",value = "品牌id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "friend_number",value = "好友数量" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "load_number",value = "加载次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "slide_length",value = "滑动长度" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "press_number",value = "长按次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "copy_number",value = "复制次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "qrcode_number",value = "扫二维码次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "click_number",value = "点击次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "skip_wechat_number",value = "跳转微信次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "show_number",value = "轮播次数" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "weight_rate",value = "权重占比" , dataType = "Double" ,paramType = "query"),
            @ApiImplicitParam(name = "type",value = "统计类型" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司名称" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建名称" , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间" , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人id" , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    @SysLog("修改渠道微信号分析统计")
    public R update(@YBRequestParam BssChannelStatistics bssChannelStatistics){
        /*QueryWrapper channelWrapper = new QueryWrapper<>();
        channelWrapper.eq("",bssChannelStatistics.getBrandName());
        BssChannel bssChannel = bssChannelService.getOne(channelWrapper);*/

        // 先检查ip是否存在，如果存在不算正常粉丝
        QueryWrapper ipWrapper = new QueryWrapper<>();
        ipWrapper.eq("ip_adress",bssChannelStatistics.getIpAdress());
        ipWrapper.eq("channel_code",bssChannelStatistics.getChannelCode());
        IpRecord ipRecord = ipRecordService.getOne(ipWrapper);
        if (null == ipRecord){
            QueryWrapper queryWrapper = new QueryWrapper<>();

            if (null == bssChannelStatistics.getChannelCode()){
                R.error("渠道编码不能为空");
            }

            if (null == bssChannelStatistics.getWechatCode()){
                R.error("微信编码不能为空");
            }

            queryWrapper.eq("channel_code",bssChannelStatistics.getChannelCode());
            queryWrapper.eq("wechat_code",bssChannelStatistics.getWechatCode());

            BssChannelStatistics oldBssChannelStatistics = bssChannelStatisticsService.getOne(queryWrapper);
            if (null == oldBssChannelStatistics){
                oldBssChannelStatistics = bssChannelStatistics;
            }

            Integer clickNumber = IntegerUtil.getInt(oldBssChannelStatistics.getClickNumber()) + IntegerUtil.getInt(bssChannelStatistics.getClickNumber());
            if (clickNumber > 0){
                oldBssChannelStatistics.setClickNumber(clickNumber);
            }

            Integer slideLength = IntegerUtil.getInt(oldBssChannelStatistics.getSlideLength())+ IntegerUtil.getInt(bssChannelStatistics.getSlideLength());
            if (slideLength > 0){
                oldBssChannelStatistics.setSlideLength(slideLength);
            }

            Integer copyNumber = IntegerUtil.getInt(oldBssChannelStatistics.getCopyNumber()) + IntegerUtil.getInt(bssChannelStatistics.getCopyNumber());
            if (copyNumber > 0){
                oldBssChannelStatistics.setCopyNumber(copyNumber);
            }

            Integer skipWechatNumber = IntegerUtil.getInt(oldBssChannelStatistics.getSkipWechatNumber()) + IntegerUtil.getInt(bssChannelStatistics.getSkipWechatNumber());
            if (skipWechatNumber > 0){
                oldBssChannelStatistics.setSkipWechatNumber(skipWechatNumber);
            }


            bssChannelStatisticsService.saveOrUpdate(oldBssChannelStatistics);
        }

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效渠道微信号分析统计",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "渠道微信号分析统计实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/invalid")
    @SysLog("设置为无效渠道微信号分析统计")
    public R invalid(@YBRequestParam String[] uuids){
        bssChannelStatisticsService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除渠道微信号分析统计",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "渠道微信号分析统计实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/delete")
    @SysLog("删除渠道微信号分析统计")
    public R delete(@YBRequestParam String[] uuids){
		bssChannelStatisticsService.deleteBatch(uuids);

        return R.ok();
    }

}
