package cn.luminous.squab;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;

public class AddTaskCmd implements Command<String> {

    protected String taskId;

    protected String assignee;

    private RuntimeService runtimeService;

    private TaskService taskService;

    private RepositoryService repositoryService;

    private Boolean isBefore;

    public AddTaskCmd(String taskId, String assignee, RuntimeService runtimeService, TaskService taskService, RepositoryService repositoryService, Boolean isBefore) {
        super();
        this.taskId = taskId;
        this.assignee = assignee;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.repositoryService = repositoryService;
        this.isBefore = isBefore;
    }


    @Override
    public String execute(CommandContext commandContext) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        ProcessDefinitionEntity deployedProcessDefinition = commandContext
                .getProcessEngineConfiguration()
                .getDeploymentManager()
                .findDeployedProcessDefinitionById(task.getProcessDefinitionId());
        ActivityImpl activity = deployedProcessDefinition.findActivity(task.getTaskDefinitionKey());
        ActivityBehavior activityBehavior = activity.getActivityBehavior();
        Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        ExecutionEntity ee = (ExecutionEntity) execution;
        TaskEntity te = (TaskEntity) task;

        // 查询流程定义
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());//流程定义查流程模型
        FlowElement currentElement=bpmnModel.getMainProcess().getFlowElement(task.getTaskDefinitionKey());
        



        return null;


    }
}
