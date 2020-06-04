package com.yb.cheung.modules.bss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yb.cheung.common.annotation.SysLog;
import com.yb.cheung.common.annotation.YBRequestParam;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.utils.IntegerUtil;
import com.yb.cheung.common.utils.QW;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.utils.WeightRandom;
import com.yb.cheung.modules.bss.entity.*;
import com.yb.cheung.modules.bss.service.BssChannelStatisticsService;
import com.yb.cheung.modules.bss.service.BssChannelWechatService;
import com.yb.cheung.modules.bss.service.BssWechatService;
import com.yb.cheung.modules.bss.service.IpRecordService;
import com.yb.cheung.modules.sys.entity.SysUser;
import com.yb.cheung.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"广告测试页接口"})
@RestController
@RequestMapping("/open")
@Slf4j
public class BssOpenController extends BaseController {

    @Autowired
    private IpRecordService ipRecordService;

    @Autowired
    private BssChannelStatisticsService bssChannelStatisticsService;

    @Autowired
    private BssWechatService bssWechatService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据渠道获取轮询的微信号
     */
    @ApiOperation(value = "根据渠道获取轮询的微信号",httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "channelCode",value = "渠道编码" ,required = true , dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "account",value = "用户账号" ,required = true , dataType = "String" ,paramType = "query"),
    })
    @GetMapping("/get_wechats")
    public R delete(HttpServletRequest request,@YBRequestParam @Validated BssChannel bssChannel){
        Map<String,Object> userParam = new HashMap<>();
        userParam.put("account",bssChannel.getAccount());
        SysUser sysUser = sysUserService.getOne(QW.getQW(userParam,SysUser.class));

        List<BssWechat> bssWechatList = bssWechatService.listByChannelCode(bssChannel.getChannelCode());

        List<Pair> pairList = new ArrayList<>();
        for (BssWechat wechat:bssWechatList){
            pairList.add(new Pair(wechat.getWechatCode(),wechat.getWeight()));
        }
        WeightRandom weightRandom = new WeightRandom(pairList);
        String wechatCode = (String) weightRandom.random();
        return R.ok(wechatCode);
    }

    /**
     * 提交统计
     */
    @ApiOperation(value = "提交统计",httpMethod = "POST")
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
    @PostMapping("channel/statistics")
    @SysLog("修改渠道微信号分析统计")
    public R update(@YBRequestParam BssChannelStatistics bssChannelStatistics){
        // 每个ip只能提交一次
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
            } else {
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
            }

            boolean isTrue = bssChannelStatisticsService.saveOrUpdate(oldBssChannelStatistics);
            if (isTrue){
                ipRecordService.insert(ipRecord);
            }
        }

        return R.ok();
    }
}
