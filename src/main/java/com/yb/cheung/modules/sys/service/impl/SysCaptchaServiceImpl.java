/**
 *
 *
 *
 *
 *
 */

package com.yb.cheung.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.yb.cheung.common.aspect.RedisAspect;
import com.yb.cheung.common.exception.RRException;
import com.yb.cheung.common.utils.DateUtils;
import com.yb.cheung.common.utils.RedisUtils;
import com.yb.cheung.modules.sys.dao.SysCaptchaDao;
import com.yb.cheung.modules.sys.entity.SysCaptchaEntity;
import com.yb.cheung.modules.sys.service.SysCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * 验证码
 *
 * @author cheung pangfucheung@163.com
 */
@Service("sysCaptchaService")
public class SysCaptchaServiceImpl extends ServiceImpl<SysCaptchaDao, SysCaptchaEntity> implements SysCaptchaService {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();
        System.out.println(code);
        redisUtils.set(uuid,code,5*60);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        String redisCode = redisUtils.get(uuid);
        if (code.equals(redisCode)){
            redisUtils.delete(uuid);
            return true;
        }
        return false;
    }
}
