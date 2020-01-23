package com.yb.cheung.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yb.cheung.common.annotation.SysLog;
import com.yb.cheung.common.utils.QW;
import com.yb.cheung.modules.sys.entity.SysRole;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.yb.cheung.modules.sys.entity.SysMenu;
import com.yb.cheung.modules.sys.service.SysMenuService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.annotation.YBRequestParam;


/**
 * 系统菜单信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-04 13:13:24
 */
@Api(description = "系统菜单信息")
@RestController
@RequestMapping("sys/sysmenu")
@Slf4j
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 列表
     */
    @ApiOperation(value = "系统菜单信息分页查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value = "查询条件" , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/tree")
    public R tree(@YBRequestParam Map<String, Object> params){
        List<SysMenu> menus = null;
        if (params.isEmpty()){
            menus = sysMenuService.findAllMenus(null);
        } else {
            menus = sysMenuService.list(QW.getQW(params,SysMenu.class,true));
        }
        return R.ok(menus);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "所有系统菜单信息列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value = "查询条件" , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/nav")
    public R nav(@YBRequestParam Map<String, Object> params){
        List<SysMenu> menus = sysMenuService.findAllMenuChildrens(getUser().getUuid());
        return R.ok(menus);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "所有系统菜单信息列表",httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = sysMenuService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取系统菜单信息",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query")
    })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
		SysMenu sysMenu = sysMenuService.getById(uuid);

        return R.ok().put("sysMenu", sysMenu);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存系统菜单信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "名称 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "code",value = "唯一编码 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "parent_id",value = "父id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "url",value = "菜单url集合 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "perms",value = "按钮权限集合 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "icon",value = "图标 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "type",value = "类型 1：菜单 2：权限 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "order_num",value = "父id " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    @SysLog("保存系统菜单信息")
    public R save(@YBRequestParam SysMenu sysMenu){
		sysMenuService.insert(sysMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改系统菜单信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "name",value = "名称 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "code",value = "唯一编码 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "parent_id",value = "父id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "url",value = "菜单url集合 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "perms",value = "按钮权限集合 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "icon",value = "图标 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "type",value = "类型 1：菜单 2：权限 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "order_num",value = "父id " , dataType = "Integer" ,paramType = "query"),
                @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
                @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    @SysLog("修改系统菜单信息")
    public R update(@YBRequestParam SysMenu sysMenu){
		sysMenuService.update(sysMenu);

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效系统菜单信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统菜单信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/invalid")
    @SysLog("设置为无效系统菜单信息")
    public R invalid(@YBRequestParam String[] uuids){
        sysMenuService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除系统菜单信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统菜单信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/delete")
    @SysLog("删除系统菜单信息")
    public R delete(@YBRequestParam String[] uuids){
		sysMenuService.deleteBatch(uuids);
        return R.ok();
    }

    /**
     * 根据角色id获取menu
     */
    @ApiOperation(value = "根据角色id获取menu",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
    })
    @PostMapping("/menus_role_id")
    @SysLog("根据角色id获取menu")
    public R getMenuByRoleId(@YBRequestParam String roleId){
        return R.ok(sysMenuService.findAllMenusByRoleId(roleId));
    }

}
