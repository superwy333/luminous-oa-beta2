package cn.luminous.squab.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.OaTaskApprove;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.mapper.OaTaskApproveMapper;
import cn.luminous.squab.mapper.OaTaskMapper;
import cn.luminous.squab.model.OaTaskApproveModel;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.ActivitiService;
import cn.luminous.squab.service.OaTaskApproveService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("oaTaskApproveService")
public class OaTaskApproveServiceImpl extends BaseServiceImpl<OaTaskApprove> implements OaTaskApproveService {

    @Autowired
    private OaTaskApproveMapper oaTaskApproveMapper;

    @Override
    protected IMapper<OaTaskApprove> getBaseMapper() {
        return this.oaTaskApproveMapper;
    }

    @Override
    public List<OaTaskApproveModel> queryOaTaskApproveForPrint(Long oaTaskId) throws Exception {
        return oaTaskApproveMapper.queryOaTaskApproveForPrint(oaTaskId);
    }
}
