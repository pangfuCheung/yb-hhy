package com.yb.cheung.modules.bss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.cheung.modules.bss.dao.BssChannelBrandDao;
import com.yb.cheung.modules.bss.entity.BssChannelBrand;
import com.yb.cheung.modules.bss.service.BssChannelBrandService;
import org.springframework.stereotype.Service;

@Service("bssChannelBrandService")
public class BssChannelBrandServiceImpl extends ServiceImpl<BssChannelBrandDao, BssChannelBrand> implements BssChannelBrandService {
}
