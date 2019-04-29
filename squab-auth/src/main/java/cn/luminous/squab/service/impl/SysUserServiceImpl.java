package cn.luminous.squab.service.impl;

import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.mapper.SysUserMapper;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUer> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    protected IMapper<SysUer> getBaseMapper() {
        return this.sysUserMapper;
    }

    @Override
    public List<SysUer> queryAllUsers(SysUer sysUer) {
        return sysUserMapper.queryAllUsers(sysUer);
    }
}
