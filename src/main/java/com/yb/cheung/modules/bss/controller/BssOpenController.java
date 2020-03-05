package com.yb.cheung.modules.bss.controller;

import com.yb.cheung.common.annotation.YBRequestParam;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.modules.bss.service.BssChannelStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/open")
@Slf4j
public class BssOpenController extends BaseController {

    @Autowired
    private BssChannelStatisticsService bssChannelStatisticsService;

    /**
     * 根据渠道获取轮询的微信号
     */
    @ApiOperation(value = "根据渠道获取轮询的微信号",httpMethod = "GET")
    @GetMapping("/get_wechats")
    public R delete(HttpServletRequest request){
        System.out.println(request.getRemoteHost());
        System.out.println(request.getContextPath());
        System.out.println(request.getRequestURL());
        return R.ok();
    }


}
