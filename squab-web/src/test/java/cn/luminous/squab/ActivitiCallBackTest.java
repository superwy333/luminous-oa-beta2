package cn.luminous.squab;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiCallBackTest {

    @Autowired
    private ApplicationContext ioc;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private HistoryService historyService;

    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    ManagementService managementService;



    @Test
    public void start() {
        System.out.println("【Before】Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
        Map<String,Object> variables = new HashMap<>();
        variables.put("assignee","aaa");
        processEngine.getRuntimeService()
                .startProcessInstanceByKey("test_any_node",variables); // 流程实例id传act_re_procdef.KEY
        System.out.println("【After】Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        processEngine.getTaskService().complete("120002");

    }

    @Test
    public void rollBack() {

        Map<String, Object> variables;

        HistoricTaskInstance currTask = historyService
                .createHistoricTaskInstanceQuery().taskId("115002")
                .singleResult();

        ProcessInstance instance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(currTask.getProcessInstanceId())
                .singleResult();

        variables = instance.getProcessVariables();

        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(currTask
                        .getProcessDefinitionId());

        ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
                .findActivity(currTask.getTaskDefinitionKey());

        List<PvmTransition> nextTransitionList = currActivity
                .getIncomingTransitions();

        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();

        List<PvmTransition> pvmTransitionList = currActivity
                .getOutgoingTransitions();

        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }

        pvmTransitionList.clear();

        List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
        for (PvmTransition nextTransition : nextTransitionList) {
            PvmActivity nextActivity = nextTransition.getSource();
            ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
                    .findActivity(nextActivity.getId());
            TransitionImpl newTransition = currActivity
                    .createOutgoingTransition();
            newTransition.setDestination(nextActivityImpl);
            newTransitions.add(newTransition);
        }

        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(instance.getId())
                .taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
        for (Task task : tasks) {
            taskService.claim(task.getId(), task.getAssignee());
            taskService.complete(task.getId(), variables);
            historyService.deleteHistoricTaskInstance(task.getId());
        }
        // 恢复方向
        for (TransitionImpl transitionImpl : newTransitions) {
            currActivity.getOutgoingTransitions().remove(transitionImpl);
        }
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }


    /**
     * 工作流自由跳转
     * @param taskId 要跳转到的节点名称
     * @return
     */
    private String taskRollback(String taskId){
//        String taskId = "122502";
        //根据要跳转的任务ID获取其任务
        HistoricTaskInstance hisTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        // String taskAssignee = hisTask.getAssignee();
        //进而获取流程实例
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(hisTask.getProcessInstanceId()).singleResult();
        //取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(hisTask.getProcessDefinitionId());
        //获取历史任务的Activity
        ActivityImpl hisActivity = definition.findActivity(hisTask.getTaskDefinitionKey());
        //实现跳转
        managementService.executeCommand(new JumpCmd(instance.getId(), hisActivity.getId()));
        return hisTask.getProcessInstanceId();
    }


    @Test
    public void rollBackStart() {
        String processInstanceId = "140001";
        //1.获取需要跳转的流程环节processid
        //List<HistoricTaskInstance> historicTaskInstance= historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId)
        List<HistoricTaskInstance> historicTaskInstance= historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskCreateTime().finished().asc().list();
        System.out.println(historicTaskInstance);
        //this.taskRollback(historicTaskInstance.get(0).getId()); // 这里取第一个就是跳转到第一个节点

    }




}
