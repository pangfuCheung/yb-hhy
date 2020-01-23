package com.yb.cheung.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

@Slf4j
public class PW {

    public static QueryWrapper getPW(QueryWrapper queryWrapper){
        if (null == queryWrapper){
            queryWrapper = new QueryWrapper();
        }
        String customSqlSegment = queryWrapper.getCustomSqlSegment();
        if (StringUtils.isEmpty(customSqlSegment) || customSqlSegment.indexOf("status") == -1){
            queryWrapper.eq("status",1);
        }
        return queryWrapper;
    }

}
