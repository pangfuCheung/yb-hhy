package com.yb.cheung.modules.bss.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.cheung.modules.bss.annotation.BssMethodLog;
import com.yb.cheung.common.utils.PageUtils;
import com.yb.cheung.common.utils.Query;

import com.yb.cheung.modules.bss.dao.BssWechatBrandDao;
import com.yb.cheung.modules.bss.entity.BssWechatBrand;
import com.yb.cheung.modules.bss.service.BssWechatBrandService;



@Service("bssWechatBrandService")
public class BssWechatBrandServiceImpl extends ServiceImpl<BssWechatBrandDao, BssWechatBrand> implements BssWechatBrandService {


}