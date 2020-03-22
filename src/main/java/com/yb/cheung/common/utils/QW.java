package com.yb.cheung.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yb.cheung.common.annotation.KeyWord;
import com.yb.cheung.common.annotation.QueryCondition;
import com.yb.cheung.modules.sys.entity.SysRole;
import com.yb.cheung.modules.sys.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.formula.functions.T;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class QW{

    public static QueryWrapper getQW(String column,Object param,Class clazz){
        Map<String,Object> params = new HashMap<>();
        params.put(column,param);
        return getQW(params,clazz);
    }

    public static QueryWrapper getQW(Map<String,Object> params,Class clazz){
        return getQW(params,clazz,false);
    }

    public static QueryWrapper getQW(Map<String,Object> params,Class clazz,boolean islike){
        QueryWrapper queryWrapper = new QueryWrapper();
        Set<String> set = new HashSet<>();
        if (!params.isEmpty()){
            Field fields[] = getAllFields(clazz);
            for (Field field:fields){
                for (Map.Entry<String,Object> entry:params.entrySet()){
                    //由于时间查询是时间段的查询，单独处理
                    Class timeClazz = field.getType();

                    if (timeClazz != Date[].class && entry.getKey().equals(field.getName())){
                        queryWrapper.eq(humpToLine(field.getName()),entry.getValue());
                        set.add(humpToLine(field.getName()));
                    } else if (timeClazz == Date.class && entry.getKey().startsWith(field.getName())){
                        String dateStringValue[] = entry.getValue().toString().replaceAll("\\[","")
                                .replaceAll("\\]","")
                                .replaceAll("\"","")
                                .split(",");
                        queryWrapper.ge(humpToLine(field.getName()),dateStringValue[0]);
                        queryWrapper.lt(humpToLine(field.getName()),dateStringValue[1]);
                    }
                }
            }

            if (islike){
                for (Field field:fields){
                    String fieldHumpToLineName = humpToLine(field.getName());
                    if (null != MapUtils.getString(params,"keyWord",null)
                            && !set.contains(fieldHumpToLineName)
                    ){
                        Annotation annotation = field.getAnnotation(KeyWord.class);
                        if (null != annotation ){
                            queryWrapper.or(true).like(fieldHumpToLineName,params.get("keyWord"));
                        }
                    }
                }
            }

        }
        return queryWrapper;
    }

    public static Field[] getAllFields(Class clazz){
        int asize = clazz.getDeclaredFields().length + clazz.getSuperclass().getDeclaredFields().length;
        Field fields[] = new Field[asize];
        Field thizFields[] = clazz.getDeclaredFields();
        Field superFields[] = clazz.getSuperclass().getDeclaredFields();
        int sindex = 0;
        for (int i=0;i<asize;i++){
            if (i<clazz.getDeclaredFields().length){
                fields[i] = thizFields[i];
            }
            if (i>=clazz.getDeclaredFields().length){
                fields[i] = superFields[sindex++];
            }
        }
        return fields;
    }

    public static QueryWrapper getQW(QueryWrapper queryWrapper,Class clazz,Map<String,Object> params){
        Map<String,String> fieldMap = getFieldMap(clazz);
        for (Map.Entry<String,Object> entry:params.entrySet()){
            eq(queryWrapper,fieldMap.get(entry.getKey()),entry.getValue());
        }
        return queryWrapper;
    }

    public static QueryWrapper eq(QueryWrapper queryWrapper,String column,Object val){
        queryWrapper.eq(column,val);
        return queryWrapper;
    }

    public static Map getFieldMap(Class clazz){
        Map<String,String> fieldMap = new HashMap<>();
        Field fields[] = clazz.getDeclaredFields();
        for (Field field:fields){
            fieldMap.put(field.getName(),lineToHump(field.getName()));
        }
        Field superFields[] = clazz.getSuperclass().getDeclaredFields();
        for (Field field:superFields){
            fieldMap.put(field.getName(),lineToHump(field.getName()));
        }
        return fieldMap;
    }

    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
