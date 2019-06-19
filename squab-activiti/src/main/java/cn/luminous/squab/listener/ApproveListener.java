package cn.luminous.squab.listener;

import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.OaTaskApprove;
import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.service.ActivitiService;
import cn.luminous.squab.service.OaTaskApproveService;
import cn.luminous.squab.service.OaTaskService;
import cn.luminous.squab.service.SysUserService;
import cn.luminous.squab.util.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

public class ApproveListener implements TaskListener {

    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private OaTaskService oaTaskService;
    @Autowired
    private OaTaskApproveService oaTaskApproveService;


    {
        if (activitiService == null) activitiService = (ActivitiService) SpringUtil.getObject("activitiService");
        if (sysUserService == null) sysUserService = (SysUserService) SpringUtil.getObject("sysUserService");
        if (oaTaskService == null) oaTaskService = (OaTaskService) SpringUtil.getObject("oaTaskService");
        if (oaTaskApproveService == null)
            oaTaskApproveService = (OaTaskApproveService) SpringUtil.getObject("oaTaskApproveService");

    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee;
        try {
            String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
            String assigneeConfig = delegateTask.getAssignee();
            // 查询上一个任务
            HistoricTaskInstance previousTask = activitiService.getPreviousTask(delegateTask.getProcessInstanceId());
            if (Constant.ASSIGNEE_KEY.KS.equals(assigneeConfig) ||
                    Constant.ASSIGNEE_KEY.SJBM.equals(assigneeConfig) ||
                    Constant.ASSIGNEE_KEY.FGLD.equals(assigneeConfig)
            ) {
                // 获取当前登陆人作为组织架构树的查询条件
                SysUer currentUser = (SysUer) SecurityUtils.getSubject().getPrincipal();
                assignee = sysUserService.getAssignee(currentUser.getUserCode(), assigneeConfig);
                delegateTask.setAssignee(assignee);
            } else {
                assignee = assigneeConfig;
            }
            if (previousTask != null) { // 不是第一个任务
                Map<String, Object> variables = activitiService.getVariables(delegateTask.getId()); // 获取流程变量
                String sqrCode = (String) variables.get("sqrCode");
                Boolean sameWithSqr = assignee.equals(sqrCode); // 当前审批人和申请人相同
                Boolean sameWithPrevious = assignee.equals(previousTask.getAssignee()); // 当前审批人与上一节点审批人相同
                if (sameWithSqr || sameWithPrevious) {
                    activitiService.completeTask(delegateTask.getId(), variables);
                    OaTaskApprove oaTaskApprove = new OaTaskApprove();
                    oaTaskApprove.setApprover(assignee);
                    oaTaskApprove.setApproveResult(Constant.TASK_APPROVE_RESULT.PASS);
                    oaTaskApprove.setApproveContent("责任人相同，自动通过");
                    oaTaskApprove.setActTaskId(delegateTask.getId());
                    oaTaskApprove.setApproveTime(new Date());
                    OaTask oaTask = new OaTask();
                    oaTask.setProcInstId(delegateTask.getProcessInstanceId());
                    oaTask = oaTaskService.queryOne(oaTask);
                    oaTaskApprove.setOaTaskId(oaTask.getId());
                    oaTaskApproveService.add(oaTaskApprove);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //delegateTask.setAssignee("tt"); // 异常情况把任务给管理员
        }


    }
}
