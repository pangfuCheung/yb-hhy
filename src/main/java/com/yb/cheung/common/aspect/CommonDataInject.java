package com.yb.cheung.common.aspect;

import com.alibaba.fastjson.JSON;
import com.mchange.lang.IntegerUtils;
import com.yb.cheung.common.utils.Constant;
import com.yb.cheung.common.utils.QW;
import com.yb.cheung.modules.bss.entity.*;
import com.yb.cheung.modules.bss.service.BssChannelBrandService;
import com.yb.cheung.modules.bss.service.BssChannelWechatService;
import com.yb.cheung.modules.bss.service.BssWechatBrandService;
import com.yb.cheung.modules.bss.service.BssWechatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Aspect
@Component
@Slf4j
public class CommonDataInject {

    @Autowired
    private BssChannelWechatService bssChannelWechatService;

    @Autowired
    private BssChannelBrandService bssChannelBrandService;

    @Autowired
    private BssWechatBrandService bssWechatBrandService;

    /*@Pointcut("execution(* com.yb.cheung.modules.bss.*.dao.*Dao.insert*(..))")
    private void insertCutMethod() {
    }*/

    /*@Around("insertCutMethod()")
    public Object doInsertAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            logger.info("[insert]"+arg);
            String companyId = null;
            Integer status = null;
            Field fields[] = arg.getClass().getDeclaredFields();
            Method methods[] = arg.getClass().getMethods();
            for (Method method:methods){
                if (method.getName().equals(Constant.ENTITY_METHOD_GET_STATUS)){
                    status = (Integer) method.invoke(arg);
                }
            }



            for (Field field:fields){
                field.setAccessible(true);
                if ()
                *//*if (field.getName().equals(Constant.ENTITY_FIELD_UPDATE_TIME)){
                    field.set(arg,new Date());
                } else if (field.getName().equals(Constant.ENTITY_FIELD_CREATOR_ID)){
                    field.set(arg,null);
                } else if (null == companyId && field.getName().equals(Constant.ENTITY_FIELD_COMPANY_ID)){
                    field.set(arg,null);
                } else if (null == status && field.getName().equals(Constant.ENTITY_FIELD_STATUS)){
                    field.set(arg,1);
                }*//*
            }
        }
        Object o = pjp.proceed();
        return o;
    }*/

    @Pointcut("execution(* com.yb.cheung.modules.*.dao.*Dao.update*(..))")
    private void updateCutMethod() {
    }

    @Around("updateCutMethod()")
    public Object doupdateAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            log.info("[update] : %s",arg);
            Integer status = null;
            String uuid = null;
            Method methods[] = arg.getClass().getMethods();
            for (Method method:methods){
                if (method.getName().equals(Constant.ENTITY_METHOD_GET_STATUS)){
                    status = (Integer) method.invoke(arg);
                } else if (method.getName().equals(Constant.ENTITY_METHOD_GET_UUID)){
                    uuid = (String) method.invoke(arg);
                }
            }

            // 删除中间表
            if (arg instanceof BssChannel){
                if (null != status && -1 == status){
                    log.info("删除中间表操作：{%s}",JSON.toJSONString(arg));
                    List<BssChannelWechat> bssChannelWechats = bssChannelWechatService.list(QW.getQW("channelId",uuid,BssChannelWechat.class));
                    if (!bssChannelWechats.isEmpty()){
                        for (BssChannelWechat bssChannelWechat:bssChannelWechats){
                            if (null != bssChannelWechat)
                                bssChannelWechatService.removeById(bssChannelWechat.getUuid());
                        }
                    }
                    BssChannelBrand bssChannelBrand = bssChannelBrandService.getOne(QW.getQW("channelId",uuid,BssChannelBrand.class));
                    if (null != bssChannelBrand){
                        bssChannelBrandService.removeById(bssChannelBrand.getUuid());
                    }
                }
            } else if (arg instanceof BssWechat){
                if (null != status && -1 == status){
                    log.info("删除中间表操作：{%s}",JSON.toJSONString(arg));
                    BssWechatBrand bssWechatBrand = bssWechatBrandService.getOne(QW.getQW("wechatId",uuid, BssWechatBrand.class));
                    if (null != bssWechatBrand){
                        bssWechatBrandService.removeById(bssWechatBrand.getUuid());
                    }
                }
            }
        }
        Object o = pjp.proceed();
        return o;
    }
}
