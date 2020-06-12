package com.yb.cheung.common.auth;

import com.yb.cheung.modules.sys.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JWT implements Serializable {

    private static final long serialVersionUID = 1L;

    public JWT(){}

    public JWT(String token){
        this.token = token;
    }

    //用户ID
    private String userId;
    //token
    private String token;
    //过期时间
    private Date expireTime;
    //更新时间
    private Date updateTime;
    //当前用户信息
    private SysUser sysUser;

}
