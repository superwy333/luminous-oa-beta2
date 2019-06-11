package cn.luminous.squab.service.impl;


import cn.luminous.squab.entity.OaTaskAttachment;
import cn.luminous.squab.mapper.OaTaskAttachmentMapper;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.OaTaskAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("oaTaskAttachmentService")
public class OaTaskAttachmentServiceImpl extends BaseServiceImpl<OaTaskAttachment> implements OaTaskAttachmentService {

    @Autowired
    private OaTaskAttachmentMapper oaTaskAttachmentMapper;

    @Override
    protected IMapper<OaTaskAttachment> getBaseMapper() {
        return oaTaskAttachmentMapper;
    }
}
