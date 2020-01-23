/**
 *
 *
 *
 *
 *
 */

package com.yb.cheung.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yb.cheung.modules.sys.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 *
 * @author cheung pangfucheung@163.com
 */
@Mapper
public interface SysCaptchaDao extends BaseMapper<SysCaptchaEntity> {

}
