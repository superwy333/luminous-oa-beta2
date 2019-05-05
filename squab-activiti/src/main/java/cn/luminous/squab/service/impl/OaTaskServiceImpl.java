package cn.luminous.squab.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.luminous.squab.constant.Constant;
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
import cn.luminous.squab.service.OaTaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("oaTaskService")
public class OaTaskServiceImpl extends BaseServiceImpl<OaTask> implements OaTaskService {

    @Autowired
    private OaTaskMapper oaTaskMapper;
    @Autowired
    private OaTaskApproveMapper oaTaskApproveMapper;
    @Autowired
    private OaTaskApproveService oaTaskApproveService;
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
     * TODO 这个地方需要考虑事务的问题
     */
    @Override
    public String registerTask(OaTask oaTask) throws Exception {

        // TODO 流程提交校验，如不能重复请假，或者某些流程一个人不能提交两次 这里需要抛出异常给controller捕获

        // TODO 根据bizKey分拣得出流程定义key
        String processKey = "test-process";

        // 把oaTask的表单提交数据装入流程变量
        Map<String,Object> variables =  JSONUtil.parseObj(oaTask.getData());

        // TODO 把当前登陆用户信息装入流程变量
        variables.put("post","zy");
        variables.put("assignee","008");
        // ....

        // 启动流程
        ProcessInstance processInstance = activitiService.startProcess(processKey, variables);
        oaTask.setProcInstId(processInstance.getProcessInstanceId());
        oaTask.setProcDefId(processInstance.getProcessDefinitionId());
        oaTask.setApplyName((String) variables.get("applyName"));
        oaTask.setApplyTime(DateUtil.parse((String) variables.get("applyTime")));
        oaTask.setTaskState(Constant.TASK_STATES.IN_PROCESS);


        // 记录提交的表单数据
        this.add(oaTask);
        return null;
    }


    /**
     * 通用审批
     * @param oaTaskApprove
     * @return
     * @throws Exception
     * TODO 这个地方需要考虑事务的问题
     */
    @Override
    public String approveTask(OaTaskApprove oaTaskApprove) throws Exception {
        String actTaskId = oaTaskApprove.getActTaskId();
        // 完成任务
        Map<String,Object> variables = activitiService.getVariables(actTaskId);
        activitiService.completeTask(actTaskId, variables);
        // 记录审批信息
        oaTaskApproveService.add(oaTaskApprove);
        // 判断任务是否已经结束了
        OaTask oaTask = queryById(oaTaskApprove.getOaTaskId());
        Boolean isEnd = activitiService.isEnd(oaTask.getProcInstId());
        if (isEnd) {
            oaTask.setTaskState(Constant.TASK_STATES.PASSED);
            updateByIdSelective(oaTask);
        }
        return null;
    }

    @Override
    public String rejectTask(OaTaskApprove oaTaskApprove) throws Exception {
        String actTaskId = oaTaskApprove.getActTaskId();
        Long oaTaskId = oaTaskApprove.getOaTaskId();
        activitiService.deleteTask(actTaskId);
        oaTaskApproveService.add(oaTaskApprove);
        OaTask oaTask = queryById(oaTaskApprove.getOaTaskId());
        oaTask.setTaskState(Constant.TASK_STATES.REJECTED);
        updateByIdSelective(oaTask);
        return null;
    }

    /**
     * 查询待办任务
     * @return
     * @throws Exception
     */
    @Override
    public List<OaTaskModel> queryTaskToDo() throws Exception {
        // TODO 从Shiro中获取当前登陆用户
        String userCode = "008";
        return oaTaskMapper.queryTaskToDo(userCode);
    }


    /**
     * 查询已办任务
     * @return
     * @throws Exception
     */
    @Override
    public List<OaTaskModel> queryTaskDone() throws Exception {
        // TODO 从Shiro中获取当前登陆用户
        String userCode = "008";
        return oaTaskMapper.queryTaskDone(userCode);
    }


    /**
     * 通过act_ru_task表id查询oa_task表记录
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public OaTaskModel queryTaskById(String taskId) throws Exception {
        return oaTaskMapper.queryTaskById(taskId);
    }


    /**
     * 查询审批流转记录
     * @param oaTaskId
     * @return
     */
    @Override
    public List<OaTaskApproveModel> queryTaskApproveDetails(Long oaTaskId) {
        return oaTaskApproveMapper.queryApproveDetail(oaTaskId);
    }







}
