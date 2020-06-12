package com.yb.cheung.modules.sys.service.impl;

import com.qiniu.util.Md5;
import com.yb.cheung.common.utils.*;
import com.yb.cheung.modules.sys.entity.SysCompany;
import com.yb.cheung.modules.sys.entity.SysRole;
import com.yb.cheung.modules.sys.entity.SysUserRole;
import com.yb.cheung.modules.sys.service.SysCompanyService;
import com.yb.cheung.modules.sys.service.SysRoleService;
import com.yb.cheung.modules.sys.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yb.cheung.modules.sys.dao.SysUserDao;
import com.yb.cheung.modules.sys.entity.SysUser;
import com.yb.cheung.modules.sys.service.SysUserService;
import sun.security.provider.MD5;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysCompanyService sysCompanyService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public List<SysUser> findUsersByCompanyId(String companyId) {
        return baseMapper.findUsersByCompanyId(companyId);
    }

    @Override
    public SysUser findUserById(String userId) {
        SysUser sysUser = super.getById(userId);
        List<SysRole> roles = sysRoleService.findSysRoleByUserId(userId);
        String roleIds[] = new String[roles.size()];
        for (int i=0;i<roleIds.length;i++){
            roleIds[i] = roles.get(i).getUuid();
        }
        sysUser.setRoleIds(roleIds);
        List<SysRole> roleList = sysRoleService.list();
        sysUser.setRoleList(roleList);
        return sysUser;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUser> page = this.page(
                new Query<SysUser>().getPage(params),
                QW.getQW(params,SysUser.class,true)
        );
        List<SysUser> records = page.getRecords();
        for (SysUser sysUser:records){
            SysCompany company = sysCompanyService.getById(sysUser.getCompanyId());
            if (null != company){
                sysUser.setCompanyName(company.getName());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public void insert(SysUser sysUser) {
        String salt = Md5.md5(UUID.randomUUID().toString().getBytes());
        String pwd = sysUser.getPassword();
        Sha256Hash sha256Hash = new Sha256Hash(pwd,salt);
        String sha256Pwd = sha256Hash.toHex();
        sysUser.setPassword(sha256Pwd);
        sysUser.setSalt(salt);
        save(sysUser);
    }

    @Override
    public void insertBatch(List<SysUser> sysUsers) {
        saveBatch(sysUsers);
    }

    @Override
    public void update(SysUser sysUser) {
        updateById(sysUser);
    }

    @Override
    public void updateBatch(List<SysUser> sysUsers) {
        updateBatchById(sysUsers);
    }

    @Override
    public void invalid(String id) {
        SysUser SysUser = getById(id);
        SysUser.setStatus(0);
        update(SysUser);
    }

    @Override
    public void invalidBatch(String[] ids) {
        for (String id:ids){
            SysUser SysUser = getById(id);
            SysUser.setStatus(0);
            update(SysUser);
        }
    }

    @Override
    public void delete(String id) {
        SysUser SysUser = getById(id);
        SysUser.setStatus(-1);
        update(SysUser);
    }

    @Override
    public void deleteBatch(String[] ids) {
        for (String id:ids){
            SysUser SysUser = getById(id);
            SysUser.setStatus(-1);
            update(SysUser);
        }
    }

    @Override
    public void allotRoles(SysUser sysUser) {
        String roleIds[] = sysUser.getRoleIds();
        List<SysUserRole> userRoles = new ArrayList<>();
        for (int i=0;i<roleIds.length;i++){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getUuid());
            sysUserRole.setRoleId(roleIds[i]);
            userRoles.add(sysUserRole);
        }
        sysUserRoleService.saveBatch(userRoles);
    }

    @Override
    public Boolean checkPwd(SysUser sysUser) {
        SysUser oldSysUser = super.getById(sysUser.getUuid());
        String salt = oldSysUser.getSalt();
        String oldPwd = oldSysUser.getPassword();
        if (!oldPwd.equals(new Sha256Hash(sysUser.getPassword(),salt).toHex())){
            return false;
        }
        return true;
    }

    @Override
    public Boolean alterPwd(SysUser sysUser) {
        SysUser oldSysUser = super.getById(sysUser.getUuid());
        if (checkPwd(sysUser)){
            return false;
        }
        String newPwd = sysUser.getPassword();
        String salt = oldSysUser.getSalt();
        String newSha256 = new Sha256Hash(newPwd,salt).toHex();
        oldSysUser.setPassword(newSha256);
        super.updateById(oldSysUser);
        return true;
    }
}