package com.yb.cheung.common.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yb.cheung.common.annotation.YBRequestParam;
import com.yb.cheung.common.utils.Constant;
import com.yb.cheung.common.utils.R;
import com.yb.cheung.common.utils.RedisUtils;
import com.yb.cheung.common.utils.Sha256Hash;
import com.yb.cheung.modules.sys.entity.SysMenu;
import com.yb.cheung.modules.sys.entity.SysUser;
import com.yb.cheung.modules.sys.service.SysCaptchaService;
import com.yb.cheung.modules.sys.service.SysMenuService;
import com.yb.cheung.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(description = "系统用户信息")
@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysCaptchaService sysCaptchaService;

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "登录接口",httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "key1",value = "账号" ,required = true , dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "key2",value = "密码" ,required = true , dataType = "String" ,paramType = "query")
    })
    @RequestMapping("/login.do")
    public Object login(HttpServletRequest request, @YBRequestParam Map<String, Object> loginMap){
        String account = MapUtils.getString(loginMap,"key1",null);
        String password = MapUtils.getString(loginMap,"key2",null);
        String uuid = MapUtils.getString(loginMap,"uuid","");
        String code = MapUtils.getString(loginMap,"code","");
        QueryWrapper sw = new QueryWrapper<>();
        sw.eq("account",account);
        SysUser sysUser = sysUserService.getOne(sw);
        if (null == sysUser){
            return R.error("找不到账号！");
        }
        if (!sysUser.getPassword().equals(new Sha256Hash(password, sysUser.getSalt()).toHex())){
            return R.error("密码错误！");
        }
        String redisCode = redisUtils.get(uuid);
        if (!code.equals(redisCode)){
            return R.error("验证码错误！");
        }
        String token = getRequestToken(request);
        JWT jwt = null;
        if (null != token){
            jwt = redisUtils.get(token,JWT.class);
        }
        if (null != jwt && jwt.getExpireTime().getTime() > System.currentTimeMillis()){
            return R.ok(jwt);
        }
        String userId = sysUser.getUuid();
        List<SysMenu> menus = sysMenuService.findAllMenusByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysMenu sysMenu:menus){
            if (null != sysMenu.getUrl()){
                permsSet.add(sysMenu.getUrl());
            }
            if (null != sysMenu.getPerms()){
                permsSet.add(sysMenu.getPerms());
            }
        }
        redisUtils.set(String.format(Constant.USER_MENU_KEY,userId),permsSet);
        return tokenService.createToken(sysUser.getUuid());
    }

    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }
        return token;
    }

    @ApiOperation(value = "登出接口",httpMethod = "GET")
    @GetMapping("/logout")
    public R logout(HttpServletRequest request){
        //1.拿到token
        String token = getRequestToken(request);
        redisUtils.delete(token);
        if (null == redisUtils.get(token)){
            return R.ok("注销成功！");
        }
        return R.error("注销失败！");
    }

    /**
     * 验证码
     */
    @ApiOperation(value = "获取验证码",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "随机uuid" ,required = true , dataType = "String",paramType = "query")
    })
    @GetMapping(value = "/captcha.jpg",produces = MediaType.IMAGE_JPEG_VALUE)
    public void captcha(HttpServletResponse response, @RequestParam String uuid)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache , must-revalidate");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }
}
