package com.yb.cheung.common.auth;

import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.utils.RedisUtils;
import com.yb.cheung.modules.sys.entity.SysUser;
import com.yb.cheung.modules.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TokenService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SysUserService sysUserService;

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    public R createToken(String userId){
        //生成一个token
        String tokenValue = TokenGenerator.generateValue();
        SysUser sysUser = sysUserService.getById(userId);
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        if (null != sysUser){
            JWT token = new JWT();
            token.setUserId(sysUser.getUuid());
            token.setToken(tokenValue);
            token.setExpireTime(expireTime);
            redisUtils.set(tokenValue,token,-1);
            log.info(" ---------- 用户：" + sysUser.getUsername() + " 生成token：" + tokenValue + " --------------");
            return R.ok(token);
        }
        return R.error("生成token失败！");
    }

    public R logout(String userId){
        redisUtils.delete(userId);
        return R.ok("退出成功！");
    }

}
