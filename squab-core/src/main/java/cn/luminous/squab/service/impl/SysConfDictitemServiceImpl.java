package cn.luminous.squab.service.impl;

import cn.luminous.squab.entity.SysConfDictitem;
import cn.luminous.squab.mapper.SysConfDictitemMapper;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.SysConfDictitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysConfDictitemService")
public class SysConfDictitemServiceImpl extends BaseServiceImpl<SysConfDictitem> implements SysConfDictitemService {

    @Autowired
    private SysConfDictitemMapper sysConfDictitemMapper;

    @Override
    protected IMapper<SysConfDictitem> getBaseMapper() {
        return this.sysConfDictitemMapper;
    }

    @Override
    public List<SysConfDictitem> queryByParentCode(String parentCode) {
        return sysConfDictitemMapper.queryByParentCode(parentCode);
    }

    @Override
    public SysConfDictitem queryByCode(String parentCode, String itemCode) {
        return sysConfDictitemMapper.queryByCode(parentCode, itemCode);
    }
}
