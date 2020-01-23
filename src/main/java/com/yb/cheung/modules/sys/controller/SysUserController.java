package com.yb.cheung.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import com.yb.cheung.common.annotation.SysLog;
import com.yb.cheung.common.utils.Sha256Hash;
import com.yb.cheung.modules.sys.entity.SysRole;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.yb.cheung.modules.sys.entity.SysUser;
import com.yb.cheung.modules.sys.service.SysUserService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.annotation.YBRequestParam;


/**
 * 系统用户信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-04 13:13:24
 */
@Api(description = "系统用户信息")
@RestController
@RequestMapping("sys/sysuser")
@Slf4j
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @ApiOperation(value = "系统用户信息分页查询",httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取系统用户信息",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query")
    })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
		SysUser sysUser = sysUserService.getById(uuid);

        return R.ok().put("sysUser", sysUser);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存系统用户信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",value = "账号 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "username",value = "用户名 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "password",value = "密码 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "email",value = "邮箱 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "phone",value = "手机号 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "adress",value = "地址 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "salt",value = "盐值 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    @SysLog("保存系统用户信息")
    public R save(@YBRequestParam SysUser sysUser){
		sysUserService.insert(sysUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改系统用户信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "account",value = "账号 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "username",value = "用户名 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "password",value = "密码 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "email",value = "邮箱 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "phone",value = "手机号 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "adress",value = "地址 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "salt",value = "盐值 " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
                @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
                @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
                @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    @SysLog("修改系统用户信息")
    public R update(@YBRequestParam SysUser sysUser){
		sysUserService.update(sysUser);

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效系统用户信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统用户信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/invalid")
    @SysLog("设置为无效系统用户信息")
    public R invalid(@YBRequestParam String[] uuids){
        sysUserService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除系统用户信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统用户信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/delete")
    @SysLog("删除系统用户信息")
    public R delete(@YBRequestParam String[] uuids){
		sysUserService.deleteBatch(uuids);

        return R.ok();
    }

    /**
     * 给用户分配角色
     */
    @ApiOperation(value = "给角色分配菜单权限",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "roleIds",value = "角色ids " ,required = true , dataType = "String[]" ,paramType = "query"),
    })
    @PostMapping("/allot_roles")
    @SysLog("给角色分配菜单权限")
    public R allotRoles(@YBRequestParam SysUser sysUser){
        sysUserService.allotRoles(sysUser);
        return R.ok();
    }

    /**
     * 检查密码是否与原来密码一致
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "检查密码是否与原密码一致",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统用户信息实体类" ,required = true , dataType = "SysUser" ,paramType = "body")
    })
    @PostMapping("/check_pwd")
    public R checkPwd(SysUser sysUser){
        if (sysUserService.checkPwd(sysUser)){
            return R.error("与原密码一致");
        }
        return R.ok(true);
    }

    /**
     * 修改密码
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "修改密码",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "系统用户信息实体类" ,required = true , dataType = "SysUser" ,paramType = "body")
    })
    @PostMapping("/alter_pwd")
    public R alterPassword(@YBRequestParam SysUser sysUser){
        Boolean isTure = sysUserService.alterPwd(sysUser);
        if (isTure){
            return R.ok("密码修改成功！");
        }else {
            return R.ok("与原密码一致，密码修改失败");
        }
    }

}
