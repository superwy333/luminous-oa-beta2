package cn.luminous.squab.service.impl;

import cn.hutool.core.convert.Convert;
import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.mapper.OaTaskMapper;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.ActivitiService;
import cn.luminous.squab.service.OaTaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("oaTaskService")
public class OaTaskServiceImpl extends BaseServiceImpl<OaTask> implements OaTaskService {

    @Autowired
    private OaTaskMapper oaTaskMapper;
    @Autowired
    private ActivitiService activitiService;

    @Override
    protected IMapper<OaTask> getBaseMapper() {
        return this.oaTaskMapper;
    }


    /**
     * 接收提交的申请
     * 记录
     * 启动流程
     * @return
     * @throws Exception
     */
    @Override
    public R registerTask(OaTask oaTask) throws Exception {

        // TODO 根据bizKey分拣得出流程定义key
        String processKey = "myProcess_1";

        // 把oaTask的表单提交数据装入流程变量
        Map<String,Object> variables =  (Map<String,Object>) oaTask.getData();

        // TODO 把当前登陆用户信息装入流程变量
        variables.put("post","zy");
        // ....

        // 启动流程
        ProcessInstance processInstance = activitiService.startProcess(processKey, variables);
        oaTask.setProcInstId(processInstance.getProcessInstanceId());

        // 记录提交的表单数据
        this.add(oaTask);
        return R.ok();
    }
}
