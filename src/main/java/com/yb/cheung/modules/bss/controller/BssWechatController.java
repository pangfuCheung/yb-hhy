package com.yb.cheung.modules.bss.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.ExcelBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.yb.cheung.common.annotation.SysLog;
import com.yb.cheung.common.utils.DateUtils;
import com.yb.cheung.common.utils.UpAndDownLoadUtils;
import com.yb.cheung.modules.bss.excel.listener.BssWechatListener;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.yb.cheung.modules.bss.entity.BssWechat;
import com.yb.cheung.modules.bss.service.BssWechatService;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.base.BaseController;
import com.yb.cheung.common.annotation.YBRequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 业务微信信息
 *
 * @author pangfucheung
 * @email pangfucheung@163.com
 * @date 2019-12-04 12:52:17
 */
@Api(description = "业务微信信息")
@RestController
@RequestMapping("bss/bsswechat")
@Slf4j
public class BssWechatController extends BaseController {
    @Autowired
    private BssWechatService bssWechatService;

    /**
     * 列表
     */
    @ApiOperation(value = "所有业务微信信息列表",httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "params",value = "查询条件" ,required = true , dataType = "map" ,paramType = "query")
    })
    @GetMapping("/list")
    public R list(@YBRequestParam Map<String, Object> params){
        PageUtils page = bssWechatService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据uuid获取业务微信信息",httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query")
    })
    @GetMapping("/info/uuid")
    public R info(@YBRequestParam String uuid){
		BssWechat bssWechat = bssWechatService.getById(uuid);

        return R.ok().put("bssWechat", bssWechat);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存业务微信信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent_id",value = "父id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "wechat_code",value = "微信号 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "custom_name",value = "客服名称 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "phone_number",value = "手机号 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "qq_number",value = "QQ号 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "friend_limit",value = "好友上限 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "qr_code_url",value = "二维码URL " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "offline_reason",value = "下线原因 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "bssBrandId",value = "品牌id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "bssBrandName",value = "品牌名称" , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/save")
    @SysLog("保存业务微信信息")
    public R save(@YBRequestParam BssWechat bssWechat){
		bssWechatService.insert(bssWechat);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改业务微信信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "主键id " ,required = true , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "parent_id",value = "父id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "wechat_code",value = "微信号 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "custom_name",value = "客服名称 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "phone_number",value = "手机号 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "qq_number",value = "QQ号 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "friend_limit",value = "好友上限 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "qr_code_url",value = "二维码URL " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "offline_reason",value = "下线原因 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "状态 -1:删除 0:无效 1:有效 " , dataType = "Integer" ,paramType = "query"),
            @ApiImplicitParam(name = "company_id",value = "公司id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "create_time",value = "创建时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "creator_id",value = "创建人id " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "update_time",value = "更新时间 " , dataType = "Date" ,paramType = "query"),
            @ApiImplicitParam(name = "operator_id",value = "更新人时间 " , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "bssBrandId",value = "品牌id" , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "bssBrandName",value = "品牌名称" , dataType = "String" ,paramType = "query"),
        })
    @PostMapping("/update")
    @SysLog("修改业务微信信息")
    public R update(@YBRequestParam BssWechat bssWechat){
		bssWechatService.update(bssWechat);

        return R.ok();
    }

    /**
     * 设置无效
     */
    @ApiOperation(value = "设置为无效业务微信信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "业务微信信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/invalid")
    @SysLog("设置为无效业务微信信息")
    public R invalid(@YBRequestParam String[] uuids){
        bssWechatService.invalidBatch(uuids);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除业务微信信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuids",value = "业务微信信息实体类" ,required = true , dataType = "String[]" ,paramType = "body")
    })
    @PostMapping("/delete")
    @SysLog("删除业务微信信息")
    public R delete(@YBRequestParam String[] uuids){
		bssWechatService.deleteBatch(uuids);

        return R.ok();
    }

    /**
     * 上传微信二维码
     */
    @ApiOperation(value = "上传微信二维码",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "MultipartFile ",value = "图片文件" ,required = true , dataType = "MultipartFile" ,paramType = "body")
    })
    @PostMapping("/upload")
    @SysLog("上传微信二维码")
    public R upload(HttpServletRequest request){
        return R.ok(new UpAndDownLoadUtils().getRelPathByUpload(request));
    }

    /**
     * 根据excel模板批量上传
     */
    @ApiOperation(value = "根据excel模板批量上传",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "MultipartFile ",value = "excel文件" ,required = true , dataType = "MultipartFile" ,paramType = "body")
    })
    @PostMapping("/upload_excel")
    @SysLog("根据excel模板批量上传")
    public R uploadExcel(HttpServletRequest request){
        List<File> files = new UpAndDownLoadUtils().getRealPathByUpload(request);
        for (File file:files){
            EasyExcel.read(file, BssWechat.class, new BssWechatListener(bssWechatService)).sheet().doRead();
        }
        return R.ok();
    }

    /**
     * 导出所有微信号
     */
    @ApiOperation(value = "导出所有微信号",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "MultipartFile ",value = "excel文件" ,required = true , dataType = "MultipartFile" ,paramType = "body")
    })
    @GetMapping("/download_excel")
    @SysLog("导出所有微信号")
    public R downloadExcel(@YBRequestParam Map<String,Object> params , HttpServletRequest request , HttpServletResponse response) throws IOException{
        // 获取模板excel
        String excelPath = ClassUtils.getDefaultClassLoader().getResource("").getPath().replaceAll("/","\\\\") + "static\\template\\weixin.xlsx";

        // 获取数据
        List<BssWechat> bssWechats = bssWechatService.listByParams(params);

        // 创建模板
        ServletOutputStream out = response.getOutputStream();
        WriteWorkbook writeWorkbook = new WriteWorkbook();
        writeWorkbook.setTemplateFile(new File(excelPath));
        writeWorkbook.setOutputStream(out);

        ExcelWriter excelWriter = new ExcelWriter(writeWorkbook);
        String fileName = "微信号-" + DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN);
        WriteSheet sheet = new WriteSheet();
        sheet.setClazz(BssWechat.class);
        //设置自适应宽度
        sheet.setAutomaticMergeHead(Boolean.TRUE);
        // 第一个 sheet 名称
        //sheet.setSheetName("第一个sheet");
        excelWriter.write(bssWechats,sheet);
        // 通知浏览器以附件的形式下载处理，设置返回头要注意文件名有中文
        response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("utf-8"), "utf-8" ) + ".xlsx");
        excelWriter.finish();
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        out.flush();
        return R.ok();
    }


    /**
     * 根据字典的code获取子节点的值
     */
    @ApiOperation(value = "获取微信模板",httpMethod = "GET")
    @GetMapping("/get_template")
    @SysLog("获取微信模板")
    public Object getTemplate(HttpServletRequest request)throws IOException{
        return new UpAndDownLoadUtils().downLoad(request,"weixin.xlsx");
    }

    /**
     * 根据品牌查询微信号
     */
    @ApiOperation(value = "根据品牌查询微信号",httpMethod = "GET")
    @GetMapping("/list_brand")
    @SysLog("根据品牌查询微信号")
    public R listByBrand(@YBRequestParam String brandId){
        return R.ok(bssWechatService.listByBrandId(brandId));
    }


}
