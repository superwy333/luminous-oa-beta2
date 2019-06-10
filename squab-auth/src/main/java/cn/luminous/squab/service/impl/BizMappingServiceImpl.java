package cn.luminous.squab.service.impl;

import cn.luminous.squab.entity.BizMapping;
import cn.luminous.squab.mapper.BizMappingMapper;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.BizMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bizMappingService")
public class BizMappingServiceImpl extends BaseServiceImpl<BizMapping> implements BizMappingService {


    @Autowired
    private BizMappingMapper bizMappingMapper;

    @Override
    protected IMapper<BizMapping> getBaseMapper() {
        return bizMappingMapper;
    }
}
