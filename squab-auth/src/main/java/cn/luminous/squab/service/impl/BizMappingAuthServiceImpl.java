package cn.luminous.squab.service.impl;

import cn.luminous.squab.entity.BizMappingAuth;
import cn.luminous.squab.mapper.BizMappingAuthMapper;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.BizMappingAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bizMappingAuthService")
public class BizMappingAuthServiceImpl extends BaseServiceImpl<BizMappingAuth> implements BizMappingAuthService {

    @Autowired
    private BizMappingAuthMapper bizMappingAuthMapper;

    @Override
    protected IMapper<BizMappingAuth> getBaseMapper() {
        return bizMappingAuthMapper;
    }
}
