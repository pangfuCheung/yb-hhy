package com.yb.cheung.modules.bss.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.yb.cheung.modules.bss.entity.IpRecord;
import com.yb.cheung.modules.bss.service.IpRecordService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;

import com.yb.cheung.common.annotation.YBRequestParam;

/**
 * IP访问记录
 *
 * @author pangfu
 * @email pangfucheung@163.com
 * @date 2020-03-09 09:50:26
 */
@Api("IP访问记录")
@RestController
@RequestMapping("bss/iprecord")
@Slf4j
public class IpRecordController {
    @Autowired
    private IpRecordService ipRecordService;

    /**
     * 列表
     */
    @ApiOperation(value = "IP访问记录列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = ipRecordService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取IP访问记录",httpMethod = "POST")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "uuid",value = "主键" ,required = true , dataType = "String" ,paramType = "query")
                        })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
        IpRecord ipRecord = ipRecordService.getById(uuid);

        return R.ok().put("ipRecord", ipRecord);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存IP访问记录",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "channelCode",value = "渠道编码" ,required = true , dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "wechatCode",value = "微信号" ,required = true , dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "ipAdress",value = "IP地址" ,required = true , dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "ipArea",value = "IP所属区域" ,required = true , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    public R save(@YBRequestParam IpRecord ipRecord){
        ipRecordService.insert(ipRecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改IP访问记录",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "channelCode",value = "渠道编码" ,required = true , dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "wechatCode",value = "微信号" ,required = true , dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "ipAdress",value = "IP地址" ,required = true , dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "ipArea",value = "IP所属区域" ,required = true , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    public R update(@YBRequestParam IpRecord IpRecord){
        ipRecordService.update(IpRecord);

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效IP访问记录",httpMethod = "POST")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "uuid",value = "主键" ,required = true , dataType = "String" ,paramType = "query")
                        })
    @PostMapping("/invalid")
    public R invalid(@YBRequestParam String[] uuids){
        ipRecordService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除IP访问记录",httpMethod = "POST")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "uuid",value = "主键" ,required = true , dataType = "String" ,paramType = "query")
                        })
    @PostMapping("/delete")
    public R delete(@YBRequestParam String[] uuids){
        ipRecordService.deleteBatch(uuids);

        return R.ok();
    }


}
