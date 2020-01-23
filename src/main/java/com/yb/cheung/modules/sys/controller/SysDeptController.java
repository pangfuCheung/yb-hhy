package com.yb.cheung.modules.sys.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yb.cheung.common.annotation.SysLog;
import com.yb.cheung.common.utils.PW;
import com.yb.cheung.common.utils.QW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.yb.cheung.modules.sys.entity.SysDept;
import com.yb.cheung.modules.sys.service.SysDeptService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.annotation.YBRequestParam;


/**
 * 系统部门组织信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-04 13:13:24
 */
@Api(description = "系统部门组织信息")
@RestController
@RequestMapping("sys/sysdept")
@Slf4j
public class SysDeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @ApiOperation(value = "系统部门组织信息分页查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = sysDeptService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "所有系统部门组织信息列表层级结构",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/query")
    public R query(@YBRequestParam Map<String, Object> params){
        List<SysDept> sysDepts = sysDeptService.list();
        return R.ok(sysDepts);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "系统部门组织信息树形结构查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/nav")
    public R nav(@YBRequestParam Map<String, Object> params){
        List<SysDept> sysDepts = sysDeptService.findSysDeptByNav(params);

        return R.ok(sysDepts);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取系统部门组织信息",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query")
    })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
		SysDept sysDept = sysDeptService.getById(uuid);

        return R.ok().put("sysDept", sysDept);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存系统部门组织信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "名称 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "code",value = "唯一编码 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "parent_id",value = "父id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "remark",value = "父id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    @SysLog("保存系统部门组织信息")
    public R save(@YBRequestParam SysDept sysDept){
		sysDeptService.insert(sysDept);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改系统部门组织信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "name",value = "名称 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "code",value = "唯一编码 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "parent_id",value = "父id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "remark",value = "父id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
                @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    @SysLog("修改系统部门组织信息")
    public R update(@YBRequestParam SysDept sysDept){
		sysDeptService.update(sysDept);

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效系统部门组织信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统部门组织信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/invalid")
    @SysLog("设置为无效系统部门组织信息")
    public R invalid(@YBRequestParam String[] uuids){
        sysDeptService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除系统部门组织信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统部门组织信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/delete")
    @SysLog("删除系统部门组织信息")
    public R delete(@YBRequestParam String[] uuids){
		sysDeptService.deleteBatch(uuids);

        return R.ok();
    }

}
