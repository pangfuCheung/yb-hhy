package com.yb.cheung.modules.sys.controller;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yb.cheung.common.annotation.SysLog;
import com.yb.cheung.common.utils.Constant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.yb.cheung.modules.sys.entity.SysRole;
import com.yb.cheung.modules.sys.service.SysRoleService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.annotation.YBRequestParam;


/**
 * 系统角色信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-04 13:13:23
 */
@Api(description = "系统角色信息")
@RestController
@RequestMapping("sys/sysrole")
@Slf4j
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 列表
     */
    @ApiOperation(value = "系统角色信息分页查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "所有系统角色信息列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/all")
    public R all(@YBRequestParam Map<String, Object> params){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", Constant.ENTITY_STATUS_VALID);
        return R.ok(sysRoleService.list(queryWrapper));
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取系统角色信息",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query")
    })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
		SysRole sysRole = sysRoleService.getById(uuid);

        return R.ok().put("sysRole", sysRole);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存系统角色信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "名称 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "code",value = "唯一编码 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "parent_id",value = "父id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "remark",value = "备注 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    @SysLog("保存系统角色信息")
    public R save(@YBRequestParam SysRole sysRole){
		sysRoleService.insert(sysRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改系统角色信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "name",value = "名称 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "code",value = "唯一编码 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "parent_id",value = "父id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "remark",value = "备注 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
                @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    @SysLog("修改系统角色信息")
    public R update(@YBRequestParam SysRole sysRole){
		sysRoleService.update(sysRole);

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效系统角色信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统角色信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/invalid")
    @SysLog("设置为无效系统角色信息")
    public R invalid(@YBRequestParam String[] uuids){
        sysRoleService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除系统角色信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统角色信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/delete")
    @SysLog("删除系统角色信息")
    public R delete(@YBRequestParam String[] uuids){
		sysRoleService.deleteBatch(uuids);

        return R.ok();
    }

    /**
     * 给角色分配菜单权限
     */
    @ApiOperation(value = "给角色分配菜单权限",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "menuIds",value = "菜单ids " ,required = true , dataType = "String[]" ,paramType = "query"),
    })
    @PostMapping("/allot_menus")
    @SysLog("给角色分配菜单权限")
    public R allotMenus(@YBRequestParam SysRole sysRole){
        sysRoleService.allotMenus(sysRole);
        return R.ok();
    }

}
