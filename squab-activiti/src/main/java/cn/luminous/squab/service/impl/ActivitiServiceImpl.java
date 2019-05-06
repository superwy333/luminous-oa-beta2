package cn.luminous.squab.service.impl;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.service.ActivitiService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("activitiService")
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;


    /**
     * 启动流程
     * @param processKey 流程定义key
     * @param variables 流程变量
     * @return
     * @throws Exception
     */
    @Override
    public ProcessInstance startProcess(String processKey, Map<String, Object> variables) throws Exception {
        return processEngine.getRuntimeService()
                .startProcessInstanceByKey(processKey,variables); // 流程实例id传act_re_procdef.KEY
    }

    /**
     * 获取流程变量
     * @param actTaskId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getVariables(String actTaskId) throws Exception{
        return taskService.getVariables(actTaskId);
    }

    /**
     * 完成任务
     * @param actTaskId
     * @throws Exception
     */
    @Override
    public void completeTask(String actTaskId) throws Exception {
        processEngine.getTaskService().complete(actTaskId);

    }


    /**
     * 完成任务
     * @param actTaskId
     * @param variables
     * @throws Exception
     */
    @Override
    public void completeTask(String actTaskId, Map<String, Object> variables) throws Exception {
        processEngine.getTaskService().complete(actTaskId, variables);
    }

    @Override
    public void deleteTask(String actTaskId) throws Exception {
        Task task = taskService.createTaskQuery().taskId(actTaskId).singleResult();
        runtimeService.deleteProcessInstance(task.getProcessInstanceId(), "Reject");
    }

    /**
     * 当前任务流程实例是否已经结束
     * @param procInstId
     * @return
     */
    @Override
    public Boolean isEnd(String procInstId) throws Exception{
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).singleResult();
        if (processInstance==null) { // 结束
            return true;
        }else { // 未结束
            return false;
        }
    }

    @Override
    public InputStream getDiagramOrgin(String deployId) throws Exception{
        //Model modelData = repositoryService.getModel(modelId);
//        Deployment deployment = repositoryService
//                .createDeploymentQuery()
//                .deploymentId(modelData.getDeploymentId()).singleResult();
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployId).singleResult();
        String procDefId = processDefinition.getId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
        InputStream imageStream = diagramGenerator.
                generateDiagram(bpmnModel,
                        "png",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        "宋体","宋体","宋体",
                        null,
                        1.0);
        return imageStream;
    }
}
