package com.yb.cheung.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yb.cheung.common.annotation.QueryCondition;
import com.yb.cheung.common.annotation.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.yb.cheung.modules.sys.entity.SysCompany;
import com.yb.cheung.modules.sys.service.SysCompanyService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.annotation.YBRequestParam;


/**
 * 系统公司信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-04 13:13:24
 */
@Api(description = "系统公司信息")
@RestController
@RequestMapping("sys/syscompany")
@Slf4j
public class SysCompanyController extends BaseController {
    @Autowired
    private SysCompanyService sysCompanyService;

    /**
     * 列表
     */
    @ApiOperation(value = "系统公司信息分页查询",httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = sysCompanyService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取系统公司信息",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query")
    })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
		SysCompany sysCompany = sysCompanyService.getById(uuid);

        return R.ok().put("sysCompany", sysCompany);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存系统公司信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent_id",value = "父id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "name",value = "公司名称 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "code",value = "公司编码 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "adress",value = "公司地址 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "remark",value = "备注 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    @SysLog("保存系统公司信息")
    public R save(@YBRequestParam SysCompany sysCompany){
		sysCompanyService.insert(sysCompany);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改系统公司信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "parent_id",value = "父id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "name",value = "公司名称 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "code",value = "公司编码 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "adress",value = "公司地址 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "remark",value = "备注 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "status",value = "状态 " , dataType = "Integer" ,paramType = "query"),
                @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    @SysLog("修改系统公司信息")
    public R update(@YBRequestParam SysCompany sysCompany){
		sysCompanyService.update(sysCompany);

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效系统公司信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统公司信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/invalid")
    @SysLog("设置为无效系统公司信息")
    public R invalid(@YBRequestParam String[] uuids){
        sysCompanyService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除系统公司信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统公司信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/delete")
    @SysLog("删除系统公司信息")
    public R delete(@YBRequestParam String[] uuids){
		sysCompanyService.deleteBatch(uuids);

        return R.ok();
    }

}
