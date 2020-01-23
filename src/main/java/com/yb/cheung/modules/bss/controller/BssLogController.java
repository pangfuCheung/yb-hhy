package com.yb.cheung.modules.bss.controller;

import java.util.Arrays;
import java.util.Map;

import com.yb.cheung.common.annotation.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.yb.cheung.modules.bss.entity.BssLog;
import com.yb.cheung.modules.bss.service.BssLogService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.annotation.YBRequestParam;


/**
 * 业务操作日志信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-04 12:52:17
 */
@Api(description = "业务操作日志信息")
@RestController
@RequestMapping("bss/bsslog")
@Slf4j
public class BssLogController extends BaseController {
    @Autowired
    private BssLogService bssLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "所有业务操作日志信息列表",httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = bssLogService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取业务操作日志信息",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query")
    })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
		BssLog bssLog = bssLogService.getById(uuid);

        return R.ok().put("bssLog", bssLog);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存业务操作日志信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "op_id",value = "操作人id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "op_name",value = "操作人名称 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "content",value = "操作内容 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    @SysLog("保存业务操作日志信息")
    public R save(@YBRequestParam BssLog bssLog){
		bssLogService.insert(bssLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改业务操作日志信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "op_id",value = "操作人id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "op_name",value = "操作人名称 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "content",value = "操作内容 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
                @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    @SysLog("修改业务操作日志信息")
    public R update(@YBRequestParam BssLog bssLog){
		bssLogService.update(bssLog);

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效业务操作日志信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "业务操作日志信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/invalid")
    @SysLog("设置为无效业务操作日志信息")
    public R invalid(@YBRequestParam String[] uuids){
        bssLogService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除业务操作日志信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "业务操作日志信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/delete")
    @SysLog("删除业务操作日志信息")
    public R delete(@YBRequestParam String[] uuids){
		bssLogService.deleteBatch(uuids);

        return R.ok();
    }

}
