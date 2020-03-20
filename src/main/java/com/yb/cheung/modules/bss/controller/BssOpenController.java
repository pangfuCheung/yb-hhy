package com.yb.cheung.modules.bss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yb.cheung.common.annotation.YBRequestParam;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.utils.QW;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.modules.bss.entity.BssChannelWechat;
import com.yb.cheung.modules.bss.entity.BssWechat;
import com.yb.cheung.modules.bss.service.BssChannelStatisticsService;
import com.yb.cheung.modules.bss.service.BssChannelWechatService;
import com.yb.cheung.modules.bss.service.BssWechatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/open")
@Slf4j
public class BssOpenController extends BaseController {

    @Autowired
    private BssChannelWechatService bssChannelWechatService;
    @Autowired
    private BssWechatService bssWechatService;

    /**
     * 根据渠道获取轮询的微信号
     */
    @ApiOperation(value = "根据渠道获取轮询的微信号",httpMethod = "GET")
    @GetMapping("/get_wechats")
    public R delete(HttpServletRequest request){
        System.out.println(request.getRemoteHost());
        System.out.println(request.getContextPath());
        System.out.println(request.getRequestURL());
        String channelCode = request.getParameter("channelCode");

        QueryWrapper queryWrapper = new QueryWrapper<>().eq("channel_code",channelCode);
        List<BssChannelWechat>  bssChannelWechats = bssChannelWechatService.list(queryWrapper);
        List<BssWechat> bssWechats = new ArrayList<>();
        for (int i=0;i<bssChannelWechats.size();i++){
            String wechatId = bssChannelWechats.get(i).getWechatId();
            bssWechats.add(bssWechatService.getById(wechatId));
        }
        return R.ok(bssWechats);
    }




}
