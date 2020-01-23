package com.yb.cheung.modules.sys.controller;

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

import com.yb.cheung.modules.sys.entity.SysDictionary;
import com.yb.cheung.modules.sys.service.SysDictionaryService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.annotation.YBRequestParam;


/**
 * 
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2020-01-03 14:32:35
 */
@Api(description = "")
@RestController
@RequestMapping("sys/sysdictionary")
@Slf4j
public class SysDictionaryController extends BaseController {
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 列表
     */
    @ApiOperation(value = "所有列表",httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = sysDictionaryService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid",value = "uuid" ,required = true , dataType = "String" ,paramType = "query")
    })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
		SysDictionary sysDictionary = sysDictionaryService.getById(uuid);

        return R.ok().put("sysDictionary", sysDictionary);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",value = "父id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "code",value = "字典的code" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "value",value = "字典的值" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "sort_no",value = "排序编号" , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态 有效 -1 无效 -0" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "remark",value = "备注" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建时间" , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间" , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "操作人id" , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    @SysLog("保存")
    public R save(@YBRequestParam SysDictionary sysDictionary){
		sysDictionaryService.insert(sysDictionary);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "uuid" ,required = true , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "pid",value = "父id" , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "code",value = "字典的code" , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "value",value = "字典的值" , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "sort_no",value = "排序编号" , dataType = "Integer" ,paramType = "query"),
                @ApiImplicitParam(name = "status",value = "状态 有效 -1 无效 -0" , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "remark",value = "备注" , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "company_id",value = "公司id" , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "create_time",value = "创建时间" , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "creator_id",value = "创建人" , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "update_time",value = "更新时间" , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "operator_id",value = "操作人id" , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    @SysLog("修改")
    public R update(@YBRequestParam SysDictionary sysDictionary){
		sysDictionaryService.update(sysDictionary);

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/invalid")
    @SysLog("设置为无效")
    public R invalid(@YBRequestParam String[] uuids){
        sysDictionaryService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/delete")
    @SysLog("删除")
    public R delete(@YBRequestParam String[] uuids){
		sysDictionaryService.deleteBatch(uuids);

        return R.ok();
    }

}
