package cn.luminous.squab.service.impl;

import cn.luminous.squab.entity.BizMapping;
import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.service.ActivitiService;
import cn.luminous.squab.service.BizMappingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("activitiService")
@Transactional
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private BizMappingService bizMappingService;
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
    @Autowired
    private HistoryService historyService;
    @Autowired
    ManagementService managementService;


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

    /**
     * 根据流程实例id获取流程流转经过的节点，即可以回退的节点
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    @Override
    public List<HistoricTaskInstance> getCallBackNodes(String processInstanceId) throws Exception {
        return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskCreateTime().finished().asc().list();
    }

    @Override
    public void callBackTaskToHisTask(String hisTaskId) throws Exception {
        HistoricTaskInstance hisTask = historyService.createHistoricTaskInstanceQuery().taskId(hisTaskId).singleResult();
        // String taskAssignee = hisTask.getAssignee();
        //进而获取流程实例
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(hisTask.getProcessInstanceId()).singleResult();
        //取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(hisTask.getProcessDefinitionId());
        //获取历史任务的Activity
        ActivityImpl hisActivity = definition.findActivity(hisTask.getTaskDefinitionKey());
        //实现跳转
        managementService.executeCommand(new JumpCmd(instance.getId(), hisActivity.getId()));
        //return hisTask.getProcessInstanceId();

    }

    @Override
    public void modeDeploy(String bizKey, String modelId, Long formId) throws Exception {

        // 检验bizKey是否重复发布
        BizMapping bizMapping = new BizMapping();
        bizMapping.setBizKey(bizKey);

        List<BizMapping> bizMappingList = bizMappingService.query(bizMapping);
        if (bizMappingList.size()>=1) throw new Exception("bizKey已存在");

        // 发布模型
        //获取模型
        Model modelData = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
        if (bytes == null) {
            throw new Exception("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if(model.getProcesses().size()==0){
            throw new Exception("数据模型不符要求，请至少设计一条主线流程。");
        }
        // // 检验processKey是否重复发布
        String processKey = model.getMainProcess().getId();
        bizMapping = new BizMapping();
        bizMapping.setProcessKey(processKey);
        bizMappingList = bizMappingService.query(bizMapping);
        if (bizMappingList.size()>=1) throw new Exception("该模型已部署");

        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        // 新增bizMapping
        bizMapping = new BizMapping();
        bizMapping.setProcessKey(processKey);
        bizMapping.setFormId(String.valueOf(formId));
        bizMapping.setBizKey(bizKey);
        bizMappingService.add(bizMapping);
    }
}
