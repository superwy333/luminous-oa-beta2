package cn.luminous.squab.listener;

import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.OaTaskApprove;
import cn.luminous.squab.service.ActivitiService;
import cn.luminous.squab.service.OaTaskApproveService;
import cn.luminous.squab.service.OaTaskService;
import cn.luminous.squab.service.SysUserService;
import cn.luminous.squab.util.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

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
        if (oaTaskApproveService == null) oaTaskApproveService = (OaTaskApproveService) SpringUtil.getObject("oaTaskApproveService");

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
            ) { // 只有上面三种情况才需要动态设置负责人
//                if (previousTask == null) { // 第一个节点
//                    // 获取当前登陆人作为组织架构树的查询条件
//                    Subject subject = SecurityUtils.getSubject();
//                    String userCode = (String) subject.getPrincipal();
//                    assignee = sysUserService.getAssignee(userCode, assigneeConfig);
//                } else { // 后续节点
//                    // 获取任务的提交人作为组织架构树的查询条件
//                    OaTask oaTask = new OaTask();
//                    oaTask.setProcInstId(delegateTask.getProcessInstanceId());
//                    oaTask = oaTaskService.queryOne(oaTask);
//                    assignee = sysUserService.getAssignee(oaTask.getApplyCode(), assigneeConfig);
//                }
                // 获取当前登陆人作为组织架构树的查询条件
                Subject subject = SecurityUtils.getSubject();
                String userCode = (String) subject.getPrincipal();
                assignee = sysUserService.getAssignee(userCode, assigneeConfig);
                delegateTask.setAssignee(assignee);
            } else {
                assignee = assigneeConfig;
            }

            // 查看上一个任务的指派人和下一个任务的指派人相同，则自动完成下一个任务
            if (previousTask != null && previousTask.getAssignee().equals(assignee)) {
                Map<String, Object> variables = activitiService.getVariables(delegateTask.getId());
                activitiService.completeTask(delegateTask.getId(), variables);
                OaTaskApprove oaTaskApprove = new OaTaskApprove();
                oaTaskApprove.setApprover(assignee);
                oaTaskApprove.setApproveResult(Constant.TASK_APPROVE_RESULT.PASS);
                oaTaskApprove.setApproveContent("责任人相同，自动通过");
                oaTaskApprove.setActTaskId(delegateTask.getId());
                OaTask oaTask = new OaTask();
                oaTask.setProcInstId(delegateTask.getProcessInstanceId());
                oaTask = oaTaskService.queryOne(oaTask);
                oaTaskApprove.setOaTaskId(oaTask.getId());
                oaTaskApproveService.add(oaTaskApprove);
            }
            // 直接完成可以吗？可以！
            //activitiService.completeTask(delegateTask.getId());
        } catch (Exception e) {
            e.printStackTrace();
            delegateTask.setAssignee("tt"); // 异常情况把任务给管理员
        }


    }
}
