package com.yb.cheung.common.utils;

import java.util.ArrayList;
import java.util.List;

public class Constant {

    /**
     * 请求成功
     */
    public static final int SECCUSS = 200;
    public static final String MSG_SECCUSS = "请求成功";

    /**
     * 程序异常
     */
    public static final int ERROR = 500;
    public static final String MSG_ERROR = "程序发生异常，请联系管理员";

    /**
     * 未登录/登录超时
     */
    public static final int UNLOGIN = 401;
    public static final String MSG_UNLOGIN = "未登录或者登录超时";

    /**
     * 没有权限
     */
    public  static final int UNACCESS = 201;
    public static final String MSG_UNACCESS = "用户没有权限";

    /**
     * 状态名
     */
    public static final String CODE = "code";

    /**
     * 说明数据
     */
    public static final String MSG = "msg";

    /**
     * 返回数据
     */
    public static final String RESULT = "result";

    /**
     * 返回数据类型
     */
    public static final String RESULT_TYPE = "resultType";


    /** 超级管理员ID */
    public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";

    /**
     * 忽略权限的菜单集合ID
     */
    public static final String IGNORE_MENU_ID_LIKE = "open";

    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // 菜单的redis的key
    public static final String MENU_KEY = "allMenus";

    // 忽略菜单的key
    public static final String MENU_IGNORE_KEY = "allIgnoreMenus";

    //mybatis参数处理器
    public static final String PARAMETER_KEY_COMPANY_ID = "companyId";

    public static final String PARAMETER_KEY_CREATE_TIME = "createTime";

    public static final String PARAMETER_KEY_UPDATE_TIME = "updateTime";

    public static final String PARAMETER_KEY_CREATOR_ID = "creatorId";

    public static final String PARAMETER_KEY_OPERATOR_ID = "operatorId";

    //无效
    public static final int ENTITY_STATUS_INVALID = 0;

    //有效
    public static final int ENTITY_STATUS_VALID = 1;

    //删除
    public static final int ENTITY_STATUS_DELETE = -1;

    public static final String SQL_STR_STATUS = "status";

    //查询标识
    public static final String SQL_STR_WHERE = "WHERE";

    // 获取companyId方法名
    public static final String ENTITY_METHOD_GET_COMPANY_ID = "getCompanyId";

    // 过去stattus方法
    public static final String ENTITY_METHOD_GET_STATUS = "getStatus";

    // 获取uuid方法
    public static final String ENTITY_METHOD_GET_UUID = "getUuid";

    // status key
    public static final String ENTITY_FIELD_STATUS = "status";

    // companyId key
    public static final String ENTITY_FIELD_COMPANY_ID = "companyId";

    // updateTime key
    public static final String ENTITY_FIELD_UPDATE_TIME = "updateTime";

    // createTime key
    public static final String ENTITY_FIELD_CREATE_TIME = "createTime";

    // updateId key
    public static final String ENTITY_FIELD_OPERATOR_ID = "operatorId";

    // createId key
    public static final String ENTITY_FIELD_CREATOR_ID = "creatorId";

    public static final String REDIS_USER_MENUS_KEY = "USER:MENUS:{%s}";

    public static final List<Integer> orStatus = new ArrayList<>(2);

    static {
        orStatus.add(ENTITY_STATUS_INVALID);
        orStatus.add(ENTITY_STATUS_VALID);
    }

    public static String USER_MENU_KEY = "user:menu:%s";

}
