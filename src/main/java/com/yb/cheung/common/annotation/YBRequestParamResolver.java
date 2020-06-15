package com.yb.cheung.common.annotation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.StringUtils;
import com.yb.cheung.common.annotation.validate.NotEmpty;
import com.yb.cheung.common.exception.RRException;
import com.yb.cheung.common.exception.RRExceptionHandler;
import com.yb.cheung.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Component
@Slf4j
public class YBRequestParamResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(YBRequestParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Class paramClazz = methodParameter.getParameterType();
        //System.out.println(((ParameterizedType)methodParameter.getGenericParameterType()).getActualTypeArguments()[0]);
        Map<String,Object> paramMap = null;
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        List paramList = null;
        /*YBRequestParam ybRequestParam = methodParameter.getParameterAnnotation(YBRequestParam.class);
        String paramterName = ybRequestParam.vlaue();*/
        String paramterName = methodParameter.getParameterName();
        if (paramClazz == Map.class){
            //System.out.println( " map :" + JSON.toJSONString(methodParameter));
            paramMap = new HashMap<>();
            for (Iterator<String> itr = nativeWebRequest.getParameterNames(); itr.hasNext();){
                String key = itr.next();
                String[] values = nativeWebRequest.getParameterValues(key);
                String value = values[0].trim();
                if(value!=null && !"".equals(value)){//不为空则赋值
                    paramMap.put(key, value);
                }
            }
            if (paramMap.isEmpty()) {
                String jsonStr = ReadAsChars(request);
                if (!"".equals(jsonStr)){
                    paramMap = JSON.parseObject(jsonStr,Map.class);
                }
            }
            return paramMap;
        } else if (paramClazz == List.class){
            Type tType = ((ParameterizedType)methodParameter.getGenericParameterType()).getActualTypeArguments()[0];
            Class tClazz = Class.forName(tType.getTypeName());
            //System.out.println(tClazz.getName());
            paramList = new ArrayList<>();
            String jsonStr = ReadAsChars(request);
            List<HashMap> mapList = JSON.parseArray(jsonStr,HashMap.class);
            for (HashMap hashMap:mapList){
                String s = JSON.toJSONString(hashMap);
                Map<String,Object> map = JSON.parseObject(s,Map.class);
                Object obj = BeanUtils.instantiateClass(tClazz);
                Field fields[] = tClazz.getDeclaredFields();
                for (Field field:fields){
                    field.setAccessible(true);
                    for (Map.Entry<String,Object> entry:map.entrySet()){
                        String fieldName = entry.getKey();
                        if (fieldName.equals(field.getName())){
                            field.set(obj,entry.getValue());
                        }
                    }
                }
                Field superFields[] = tClazz.getSuperclass().getDeclaredFields();
                for (Field field:superFields){
                    field.setAccessible(true);
                    for (Map.Entry<String,Object> entry:map.entrySet()){
                        String fieldName = entry.getKey();
                        if (fieldName.equals(field.getName())){
                            field.set(obj,entry.getValue());
                        }
                    }
                }
                paramList.add(obj);
            }
            return paramList;
        } else if (paramClazz == String.class){
            if (paramterName != null){
                String values[] = nativeWebRequest.getParameterValues(paramterName);
                if (null != values && values.length > 0){
                    return values[0];
                }
            }
            return dealString(request);
        } else if (paramClazz == String[].class){
            if (paramterName != null){
                String values[] = nativeWebRequest.getParameterValues(paramterName);
                if (null != values && values.length > 0){
                    return values[0].split(",");
                }
            }
            return dealStringArray(request);
        } else if (paramClazz == Integer.class){
            if (paramterName != null){
                String values[] = nativeWebRequest.getParameterValues(paramterName);
                if (null != values){
                    return Integer.valueOf(values[0]);
                }
            }
            Map<String,Object> map = dealStringMap(request);
            if (!map.isEmpty()) {
                String revalue = MapUtils.getString(map,paramterName,null);
                return revalue!=null?Integer.valueOf(revalue):null;
            }
        } else if (paramClazz == Integer[].class){
            if (paramterName != null){
                String values[] = nativeWebRequest.getParameterValues(paramterName);
                if (null != values){
                    return dealIntegerArray(values);
                }
            }
            return dealIntegerArray(dealStringArray(request));
        } else if (paramClazz == Long.class){
            if (paramterName != null){
                String values[] = nativeWebRequest.getParameterValues(paramterName);
                if (null != values){
                    return Long.valueOf(values[0]);
                }
            }
            Map<String,Object> map = dealStringMap(request);
            if (!map.isEmpty()) {
                String revalue = MapUtils.getString(map,paramterName,null);
                return revalue!=null?Long.valueOf(revalue):null;
            }
        } else if (paramClazz == Long[].class){
            if (paramterName != null){
                String values[] = nativeWebRequest.getParameterValues(paramterName);
                if (null != values){
                    return dealLongArray(values);
                }
            }
            return dealLongArray(dealStringArray(request));
        } else {
            Map<String,Object> map = dealStringMap(request,nativeWebRequest);
            Type tType = methodParameter.getParameterType();
            Class tClazz = Class.forName(tType.getTypeName());
            Object obj = BeanUtils.instantiateClass(tClazz);
            Field fields[] = tClazz.getDeclaredFields();
            for (Field field:fields){
                field.setAccessible(true );
                if (null != map) {
                    for (Map.Entry<String,Object> entry:map.entrySet()){
                        if (field.getName().equals(entry.getKey())){
                            String value = (String) entry.getValue();

                            if (field.getType() == String[].class ){
                                field.set(obj,entry.getValue().toString().split(","));
                            } else if (field.getType() == List.class){
                                Type genericType = field.getGenericType();
                                if (null == genericType) {
                                    continue;
                                }

                                if (genericType instanceof ParameterizedType) {
                                    ParameterizedType pt = (ParameterizedType) genericType;
                                    // 得到泛型里的class类型对象
                                    Class actualTypeArgument = (Class)pt.getActualTypeArguments()[0];
                                    List<Object> curEleList = new ArrayList<>();

                                    Class valueClazz = entry.getValue().getClass();
                                    JSONArray jsonArray = null;
                                    if (valueClazz == String.class){
                                        jsonArray = JSONArray.parseArray((String) entry.getValue());
                                    } else {
                                        jsonArray = (JSONArray) entry.getValue();
                                    }
                                    for (int i=0;i<jsonArray.size();i++){
                                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                                        curEleList.add(jsonObject.toJavaObject(actualTypeArgument));
                                    }
                                    field.set(obj, curEleList);
                                }
                            } else if (field.getType() == Integer.class){
                                field.set(obj,Integer.valueOf(value));
                            } else if (field.getType() == Long.class){
                                field.set(obj,Long.valueOf(value));
                            } else if (field.getType() == Float.class){
                                field.set(obj,Float.valueOf(value));
                            } else if (field.getType() == Double.class){
                                field.set(obj,Double.valueOf(value));
                            } else if (field.getType() == Date.class){
                                field.set(obj, DateUtils.stringToDate(value,DateUtils.DATE_TIME_PATTERN));
                            } else if (field.getType() == String[].class){
                                field.set(obj,value.split(","));
                            }else {
                                field.set(obj,entry.getValue());
                            }
                        }
                    }
                }else {
                    for (Iterator<String> itr = nativeWebRequest.getParameterNames(); itr.hasNext();){
                        String key = itr.next();
                        String[] values = nativeWebRequest.getParameterValues(key);
                        String value = values[0].trim();
                        if(value!=null && !"".equals(value) && field.getName().equals(key)){//不为空则赋值
                            Class typeClazz = field.getType();
                            if (typeClazz == Integer.class){
                                field.set(obj,Integer.valueOf(value));
                            } else if (typeClazz == Long.class){
                                field.set(obj,Long.valueOf(value));
                            } else if (typeClazz == Float.class){
                                field.set(obj,Float.valueOf(value));
                            } else if (typeClazz == Double.class){
                                field.set(obj,Double.valueOf(value));
                            } else if (typeClazz == Date.class){
                                field.set(obj, DateUtils.stringToDate(value,DateUtils.DATE_TIME_PATTERN));
                            } else if (typeClazz == String[].class){
                                field.set(obj,value.split(","));
                            } else {
                                field.set(obj,value);
                            }
                        }
                    }
                }
            }
            Field superFields[] = tClazz.getSuperclass().getDeclaredFields();
            for (Field field:superFields){
                field.setAccessible(true );
                if (null != map) {
                    for (Map.Entry<String,Object> entry:map.entrySet()){
                        if (field.getName().equals(entry.getKey())){
                            field.set(obj,entry.getValue());
                        }
                    }
                }else {
                    for (Iterator<String> itr = nativeWebRequest.getParameterNames(); itr.hasNext();){
                        String key = itr.next();
                        String[] values = nativeWebRequest.getParameterValues(key);
                        String value = values[0].trim();
                        if(value!=null && !"".equals(value) && field.getName().equals(key)){//不为空则赋值
                            Class typeClazz = field.getType();
                            if (typeClazz == Integer.class){
                                field.set(obj,Integer.valueOf(value));
                            } else if (typeClazz == Long.class){
                                field.set(obj,Long.valueOf(value));
                            } else if (typeClazz == Float.class){
                                field.set(obj,Float.valueOf(value));
                            } else if (typeClazz == Double.class){
                                field.set(obj,Double.valueOf(value));
                            } else if (typeClazz == Date.class){
                                field.set(obj,DateUtils.stringToDate(value,DateUtils.DATE_TIME_PATTERN));
                            } else {
                                field.set(obj,value);
                            }
                        }
                    }
                }
            }

            if (methodParameter.hasParameterAnnotation(Validated.class)){
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                for (Field field:declaredFields){
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(NotEmpty.class)){
                        Object val = field.get(obj);
                        if (null == val || "".equals(val)){
                            NotEmpty annotation = field.getAnnotation(NotEmpty.class);
                            Method value = annotation.annotationType().getDeclaredMethod("message");
                            value.setAccessible(true);
                            throw new RRException(value.invoke(annotation).toString());
                        }
                    }
                }
            }

            return obj;
        }
        return null;
    }

    private Long[] dealLongArray(String[] values){
        if (values.length > 0){
            Long vals[] = new Long[values.length];
            for (int i=0;i<values.length;i++){
                vals[i] = Long.valueOf(values[i]);
            }
            return vals;
        }
        return null;
    }

    private Integer[] dealIntegerArray(String[] values){
        if (values.length > 0){
            Integer vals[] = new Integer[values.length];
            for (int i=0;i<values.length;i++){
                vals[i] = Integer.valueOf(values[i]);
            }
            return vals;
        }
        return null;
    }

    private Map<String,Object> dealStringMap(HttpServletRequest request){
        return dealStringMap(request,null);
    }

    private Map<String,Object> dealStringMap(HttpServletRequest request,NativeWebRequest nativeWebRequest){
        String requestParamStr = ReadAsChars(request);
        if (!StringUtils.isNullOrEmpty(requestParamStr)){
            return JSON.parseObject(ReadAsChars(request));
        } else if (null != nativeWebRequest){
            Map<String,Object> paramMap = new HashMap<>();
            for (Iterator<String> itr = nativeWebRequest.getParameterNames(); itr.hasNext();){
                String key = itr.next();
                String[] values = nativeWebRequest.getParameterValues(key);
                String value = values[0].trim();
                if(value!=null && !"".equals(value)){//不为空则赋值
                    paramMap.put(key, value);
                }
            }
            if (paramMap.isEmpty()) {
                String jsonStr = ReadAsChars(request);
                if (!"".equals(jsonStr)){
                    paramMap = JSON.parseObject(jsonStr,Map.class);
                }
            }
            return paramMap;
        }
        return null;
    }

    private String dealString(HttpServletRequest request){
        return ReadAsChars(request);
    }

    private String[] dealStringArray(HttpServletRequest request){
        String jsonStr = ReadAsChars(request);
        jsonStr = jsonStr.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\\"","");
        return jsonStr.split(",");
    }

    public String ReadAsChars(HttpServletRequest request){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try{
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null){
                sb.append(str);
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if (null != br){
                try{
                    br.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
