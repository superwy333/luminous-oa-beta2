package cn.luminous.squab.listener;

import cn.luminous.squab.service.ActivitiService;
import cn.luminous.squab.util.SpringUtil;
import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;

public class ApproveListener implements TaskListener {

    @Autowired
    private ActivitiService activitiService;

    {
        if (activitiService==null)
            activitiService = (ActivitiService)SpringUtil.getObject("activitiService");
    }

    /**
     * 科室
     */
    private static final String KS = "ks";
    /**
     * 上级部门
     */
    private static final String SJBM = "sjbm";
    /**
     * 分管领导
     */
    private static final String FGLD = "fgld";
    /**
     * 总经理
     */
    private static final String ZJL = "zjl";
    /**
     * 人事备案
     */
    private static final String RSBA = "rsba";

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("===========");
        System.out.println(">>>>>>>>ApproveListener<<<<<<<<");
        System.out.println("===========");
        try {
            String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
            // 查询上一个任务
            HistoricTaskInstance previousTask = activitiService.getPreviousTask(delegateTask.getProcessInstanceId());
            if (previousTask==null) { // 第一个节点
                // 获取当前登陆人作为组织架构树的查询条件


            }else { // 后续节点
                // 获取任务的提交人作为组织架构树的查询条件


            }
        }catch (Exception e) {
            e.printStackTrace();
        }


//        switch (taskDefinitionKey) {
//            case KS:
//                // 获取当前登陆人的科室，查询到科长并指定
//                break;
//            case SJBM:
//                break;
//            case FGLD:
//                break;
//            case ZJL:
//                break;
//            case RSBA:
//                break;
//        }
    }
}
