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

import com.yb.cheung.modules.bss.entity.BssBrand;
import com.yb.cheung.modules.bss.service.BssBrandService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.annotation.YBRequestParam;


/**
 * 业务品牌信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-04 12:52:17
 */
@Api(description = "业务品牌信息")
@RestController
@RequestMapping("bss/bssbrand")
@Slf4j
public class BssBrandController extends BaseController {
    @Autowired
    private BssBrandService bssBrandService;

    /**
     * 列表
     */
    @ApiOperation(value = "所有业务品牌信息列表",httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = bssBrandService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取业务品牌信息",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query")
    })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
		BssBrand bssBrand = bssBrandService.getById(uuid);

        return R.ok().put("bssBrand", bssBrand);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存业务品牌信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "品牌名称 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "remark",value = "品牌备注 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    @SysLog("保存业务品牌信息")
    public R save(@YBRequestParam BssBrand bssBrand){
		bssBrandService.insert(bssBrand);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改业务品牌信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "name",value = "品牌名称 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "remark",value = "品牌备注 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
                @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    @SysLog("修改业务品牌信息")
    public R update(@YBRequestParam BssBrand bssBrand){
		bssBrandService.update(bssBrand);

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效业务品牌信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "业务品牌信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/invalid")
    @SysLog("设置为无效业务品牌信息")
    public R invalid(@YBRequestParam String[] uuids){
        bssBrandService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除业务品牌信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "业务品牌信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/delete")
    @SysLog("删除业务品牌信息")
    public R delete(@YBRequestParam String[] uuids){
		bssBrandService.deleteBatch(uuids);

        return R.ok();
    }

}
