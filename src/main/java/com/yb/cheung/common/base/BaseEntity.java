package com.yb.cheung.common.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yb.cheung.common.utils.Constant;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ExcelIgnore
    private String uuid;

    /**
     * 状态 -1:删除 0:无效 1:有效
     */
    @ExcelIgnore
    private Integer status;

    /**
     * 公司id
     */
    @ExcelIgnore
    private String companyId;

    /**
     * 创建人id
     */
    @ExcelIgnore
    private String creatorId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date createTime;

    /**
     * 接收前端字段
     */
    @TableField(exist = false)
    private Date[] createTimeValue;

    /**
     * 更新人id
     */
    @ExcelIgnore
    private String operatorId;

    /**
     * 更新时间
     */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 接收前端字段
     */
    @TableField(exist = false)
    private Date[] updateTimeValue;

}
