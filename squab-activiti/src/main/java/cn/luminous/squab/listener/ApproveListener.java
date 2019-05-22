package cn.luminous.squab.listener;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.service.ActivitiService;
import cn.luminous.squab.service.OaTaskService;
import cn.luminous.squab.service.SysUserService;
import cn.luminous.squab.util.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class ApproveListener implements TaskListener {

    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private OaTaskService oaTaskService;


    {
        if (activitiService == null) activitiService = (ActivitiService) SpringUtil.getObject("activitiService");
        if (sysUserService == null) sysUserService = (SysUserService) SpringUtil.getObject("sysUserService");
        if (oaTaskService == null) oaTaskService = (OaTaskService) SpringUtil.getObject("oaTaskService");

    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee;
        try {
            String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
            // 查询上一个任务
            HistoricTaskInstance previousTask = activitiService.getPreviousTask(delegateTask.getProcessInstanceId());
            if (previousTask == null) { // 第一个节点
                // 获取当前登陆人作为组织架构树的查询条件
                Subject subject = SecurityUtils.getSubject();
                String userCode = (String) subject.getPrincipal();
                assignee = sysUserService.getAssignee(userCode, delegateTask.getTaskDefinitionKey());
            } else { // 后续节点
                // 获取任务的提交人作为组织架构树的查询条件
                OaTask oaTask = new OaTask();
                oaTask.setProcInstId(delegateTask.getProcessInstanceId());
                oaTask = oaTaskService.queryOne(oaTask);
                assignee = sysUserService.getAssignee(oaTask.getApplyCode(), delegateTask.getTaskDefinitionKey());
            }
            delegateTask.setAssignee(assignee);
        } catch (Exception e) {
            e.printStackTrace();
            delegateTask.setAssignee("tt"); // 异常情况把任务给管理员
        }


    }
}
