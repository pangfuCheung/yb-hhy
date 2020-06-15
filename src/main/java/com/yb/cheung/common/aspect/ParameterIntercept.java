package com.yb.cheung.common.aspect;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yb.cheung.common.utils.Constant;
import com.yb.cheung.common.utils.RedisUtils;
import com.yb.cheung.modules.sys.entity.SysUser;
import com.yb.cheung.modules.sys.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
@Slf4j
public class ParameterIntercept implements Interceptor {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        String classPath = mappedStatement.getId();
        classPath = classPath.substring(0,classPath.indexOf("Dao")).replace("dao","entity");
        Class clazz = Class.forName(classPath);
        BoundSql boundSql = (BoundSql)metaObject.getValue("delegate.boundSql");
        String oldSql = boundSql.getSql();
        /**
         * 查询语句时，默认查询有效的状态
         */
        if (SqlCommandType.SELECT == mappedStatement.getSqlCommandType() && StatementType.CALLABLE != mappedStatement.getStatementType()) {
            if (!StringUtils.isEmpty(oldSql) && oldSql.indexOf(Constant.SQL_STR_STATUS) > -1){
                if (oldSql.indexOf(Constant.SQL_STR_WHERE) >-1
                        && oldSql.indexOf(Constant.SQL_STR_STATUS,oldSql.indexOf(Constant.SQL_STR_WHERE)+1) == -1 ){
                    String sqlBegin = oldSql.substring(0,oldSql.indexOf(Constant.SQL_STR_WHERE)+Constant.SQL_STR_WHERE.length());
                    log.info( " ------- sqlBegin -------- :" + sqlBegin);
                    String sqlEnd = oldSql.substring(oldSql.indexOf(Constant.SQL_STR_WHERE)+Constant.SQL_STR_WHERE.length());
                    log.info( " ------- sqlBegin -------- :" + sqlEnd);
                    Object paramObj = boundSql.getParameterObject();
                    log.info(" ---- paramObj ---- :" + JSON.toJSONString(paramObj));
                    if (null == paramObj){
                        sqlBegin += " "+ Constant.SQL_STR_STATUS +" = " + Constant.ENTITY_STATUS_VALID;
                    } else if (sqlEnd.indexOf(Constant.SQL_STR_STATUS) == -1){
                        sqlBegin += " status = " + Constant.ENTITY_STATUS_VALID + " AND ";
                    }
                    String sql = sqlBegin + sqlEnd;
                    log.info(" -------- sql ------------- :" + sql);
                    metaObject.setValue("delegate.boundSql.sql", sql);
                } else  if (oldSql.indexOf(Constant.SQL_STR_WHERE) == -1){
                    String sql = oldSql + " " + Constant.SQL_STR_WHERE + " " + Constant.SQL_STR_STATUS +" = " + Constant.ENTITY_STATUS_VALID;
                    metaObject.setValue("delegate.boundSql.sql", sql);
                }
            }
        }
        /**
         * 对插入语句进行拦截修改，插入时，如果companyId为空，则把当前用户所属的companyId进行设值，自动设置创建时间，自动设置创建人
         */
        else if (SqlCommandType.INSERT == mappedStatement.getSqlCommandType() && StatementType.CALLABLE != mappedStatement.getStatementType()){
            String insertSql = oldSql;
            if (isEixstFieldName(clazz,Constant.ENTITY_FIELD_STATUS)){
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                ParameterMapping.Builder parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(),Constant.ENTITY_FIELD_STATUS,Integer.class);
                insertSql = dealSqlAndParamter(insertSql,Constant.ENTITY_FIELD_STATUS,parameterMappings,parameterMapping);
                metaObject.setValue("delegate.boundSql.sql", insertSql);
                metaObject.setValue("delegate.boundSql.parameterMappings",parameterMappings);
                Object parameterObject = metaObject.getValue("delegate.boundSql.parameterObject");
                MetaObject parameterObjectMetaObject = SystemMetaObject.forObject(parameterObject);
                parameterObjectMetaObject.setValue(Constant.ENTITY_FIELD_STATUS,1);
                metaObject.setValue("delegate.boundSql.parameterObject",parameterObject);
            }

            if (isEixstFieldName(clazz,Constant.ENTITY_FIELD_COMPANY_ID)){
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                ParameterMapping.Builder parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(),Constant.ENTITY_FIELD_COMPANY_ID,String.class);
                insertSql = dealSqlAndParamter(insertSql,Constant.ENTITY_FIELD_COMPANY_ID,parameterMappings,parameterMapping);
                metaObject.setValue("delegate.boundSql.sql", insertSql);
                metaObject.setValue("delegate.boundSql.parameterMappings",parameterMappings);
                Object parameterObject = metaObject.getValue("delegate.boundSql.parameterObject");
                MetaObject parameterObjectMetaObject = SystemMetaObject.forObject(parameterObject);
                Object companyId = parameterObjectMetaObject.getValue(Constant.ENTITY_FIELD_COMPANY_ID);
                if (null == companyId && null != SecurityUtils.getSubject().getPrincipal()){
                    parameterObjectMetaObject.setValue(Constant.ENTITY_FIELD_COMPANY_ID, ((SysUser)SecurityUtils.getSubject().getPrincipal()).getCompanyId());
                }
                metaObject.setValue("delegate.boundSql.parameterObject",parameterObject);
            }

            if (isEixstFieldName(clazz,Constant.ENTITY_FIELD_CREATOR_ID)){
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                ParameterMapping.Builder parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(),Constant.ENTITY_FIELD_CREATOR_ID,String.class);
                insertSql = dealSqlAndParamter(insertSql,Constant.ENTITY_FIELD_CREATOR_ID,parameterMappings,parameterMapping);
                metaObject.setValue("delegate.boundSql.sql", insertSql);
                metaObject.setValue("delegate.boundSql.parameterMappings",parameterMappings);
                Object parameterObject = metaObject.getValue("delegate.boundSql.parameterObject");
                metaObject.setValue("delegate.boundSql.parameterObject",parameterObject);
            }

            if (isEixstFieldName(clazz,Constant.ENTITY_FIELD_CREATE_TIME)){
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                ParameterMapping.Builder parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(),Constant.ENTITY_FIELD_CREATE_TIME,Date.class);
                insertSql = dealSqlAndParamter(insertSql,Constant.ENTITY_FIELD_CREATE_TIME,parameterMappings,parameterMapping);
                metaObject.setValue("delegate.boundSql.sql", insertSql);
                metaObject.setValue("delegate.boundSql.parameterMappings",parameterMappings);
                Object parameterObject = metaObject.getValue("delegate.boundSql.parameterObject");
                MetaObject parameterObjectMetaObject = SystemMetaObject.forObject(parameterObject);
                parameterObjectMetaObject.setValue(Constant.ENTITY_FIELD_CREATE_TIME, new Date());
                metaObject.setValue("delegate.boundSql.parameterObject",parameterObject);
            }
        }
        /**
         * 对更新语句进行拦截修改，插入时，自动设置更新时间，自动设置操作人
         */
        else if (SqlCommandType.UPDATE == mappedStatement.getSqlCommandType() && StatementType.CALLABLE != mappedStatement.getStatementType()){
            String updateSql = oldSql;
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            List<ParameterMapping> myParameterMappings = new ArrayList<>();

            /*if (isEixstFieldName(clazz,Constant.ENTITY_FIELD_COMPANY_ID)){
                Object parameterObject = metaObject.getValue("delegate.boundSql.parameterObject");
                MetaObject parameterObjectMetaObject = SystemMetaObject.forObject(parameterObject);
                Object companyId = metaObject.getValue("delegate.boundSql.parameterObject.et."+Constant.ENTITY_FIELD_COMPANY_ID);
                if (null == companyId && null != SecurityUtils.getSubject().getPrincipal()){
                    parameterObjectMetaObject.setValue(Constant.ENTITY_FIELD_COMPANY_ID, ((SysUser)SecurityUtils.getSubject().getPrincipal()).getCompanyId());
                    metaObject.setValue("delegate.boundSql.parameterObject",parameterObject);
                }
            }*/

            if (isEixstFieldName(clazz,Constant.ENTITY_FIELD_OPERATOR_ID)){
                parameterMappings = boundSql.getParameterMappings();
                ParameterMapping.Builder parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(),"et." + Constant.ENTITY_FIELD_OPERATOR_ID,String.class);
                updateSql = dealSqlAndParamter(updateSql,Constant.ENTITY_FIELD_OPERATOR_ID,parameterMappings,parameterMapping);
                metaObject.setValue("delegate.boundSql.sql", updateSql);
                metaObject.setValue("delegate.boundSql.parameterMappings",parameterMappings);

                Object etObject = metaObject.getValue("delegate.boundSql.parameterObject.et");
                MetaObject etObjectMetaObject = SystemMetaObject.forObject(etObject);
                if (null != SecurityUtils.getSubject().getPrincipal()){
                    etObjectMetaObject.setValue(Constant.ENTITY_FIELD_OPERATOR_ID, ((SysUser)SecurityUtils.getSubject().getPrincipal()).getUuid());
                    metaObject.setValue("delegate.boundSql.parameterObject.et",etObject);
                }
            }

            if (isEixstFieldName(clazz,Constant.ENTITY_FIELD_UPDATE_TIME)){
                parameterMappings = boundSql.getParameterMappings();
                ParameterMapping.Builder parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(),"et." + Constant.ENTITY_FIELD_UPDATE_TIME,Date.class);
                updateSql = dealSqlAndParamter(updateSql,Constant.ENTITY_FIELD_UPDATE_TIME,parameterMappings,parameterMapping);
                metaObject.setValue("delegate.boundSql.sql", updateSql);
                metaObject.setValue("delegate.boundSql.parameterMappings",parameterMappings);
                Object parameterObject = metaObject.getValue("delegate.boundSql.parameterObject");

                MetaObject parameterObjectMetaObject = SystemMetaObject.forObject(parameterObject);
                parameterObjectMetaObject.setValue(Constant.ENTITY_FIELD_UPDATE_TIME, new Date());
                metaObject.setValue("delegate.boundSql.parameterObject",parameterObject);

                Object etObject = metaObject.getValue("delegate.boundSql.parameterObject.et");
                MetaObject etObjectMetaObject = SystemMetaObject.forObject(etObject);
                etObjectMetaObject.setValue(Constant.ENTITY_FIELD_UPDATE_TIME, new Date());
                metaObject.setValue("delegate.boundSql.parameterObject.et",etObject);
            }

            /**
             * 跟新操作时，对于Parameters列表的顺序要遵循update语句中占位符的顺序，所以需要重新创建列表
             */
            String updateFields[] = updateSql.substring(updateSql.lastIndexOf("SET")+3,updateSql.indexOf("WHERE"))
                    .replace("=","").replace("?","").replaceAll("\n","").replaceAll(" ","").trim()
                    .split(",");
            for (String s:updateFields){
                for (ParameterMapping p: parameterMappings){
                    if (p.getProperty().equals("et." + lineToHump(s))){
                        myParameterMappings.add(p);
                        break;
                    }
                }
            }
            String whereFields[] = updateSql.substring(updateSql.indexOf("WHERE")+5,updateSql.length())
                    .replace("=","").replace("?","").replaceAll("\n","").replaceAll(" ","").trim()
                    .split(",");
            for (String s:whereFields){
                for (ParameterMapping p: parameterMappings){
                    if (p.getProperty().equals("et." + lineToHump(s))){
                        myParameterMappings.add(p);
                        break;
                    }
                }
            }
            metaObject.setValue("delegate.boundSql.parameterMappings",myParameterMappings);
        } else {
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    /**
     * 判断字段是否存在
     * @param clazz
     * @param fieldName
     * @return
     */
    public boolean isEixstFieldName(Class clazz,String fieldName){
        Field fields[] = clazz.getDeclaredFields();
        for (Field field:fields){
            if (fieldName.equals(field.getName())){
                return true;
            }
        }
        Field superFields[] = clazz.getSuperclass().getDeclaredFields();
        for (Field field:superFields){
            if (fieldName.equals(field.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接insert或者update语句
     * @param sql
     * @param fieldName
     * @param parameterMappings
     * @param parameterMapping
     * @return
     */
    public String dealSqlAndParamter(String sql,String fieldName,List<ParameterMapping> parameterMappings,ParameterMapping.Builder parameterMapping){
        String insertSql = null;
        if (!StringUtils.isEmpty(sql) && sql.indexOf(humpToLine(fieldName)) == -1 ){
            if (sql.indexOf(SqlCommandType.INSERT.toString()) > -1){
                parameterMappings.add(parameterMapping.build());
                insertSql = sql.substring(0,sql.indexOf(")")) + " ," + humpToLine(fieldName) + " ";
                insertSql += sql.substring(sql.indexOf(")"),sql.lastIndexOf("(")+1);
                insertSql += " ?, " + sql.substring(sql.lastIndexOf("(")+1,sql.length());
            } else if (sql.indexOf(SqlCommandType.UPDATE.toString()) > -1){
                parameterMappings.add(parameterMapping.build());
                int whereIndex = sql.indexOf("WHERE");
                sql = sql.indexOf(" SET ") > -1? sql : sql.substring(0,whereIndex) + " SET " + sql.substring(whereIndex);
                insertSql = sql.substring(0,sql.indexOf(Constant.SQL_STR_WHERE));
                boolean hasUpdateFeild = false;
                for (ParameterMapping p:parameterMappings){
                    String property = p.getProperty().substring(p.getProperty().indexOf(".")+1);
                    if (insertSql.indexOf(humpToLine(property)) > -1){
                        hasUpdateFeild = true;
                    }
                }
                insertSql += (hasUpdateFeild?",":"") + humpToLine(fieldName) + "=? ";
                insertSql += sql.substring(sql.lastIndexOf(Constant.SQL_STR_WHERE),sql.length());
            }
        } else {
            insertSql = sql;
        }
        return insertSql;
    }

    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    public String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
