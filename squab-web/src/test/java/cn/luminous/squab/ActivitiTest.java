package cn.luminous.squab;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;
import java.util.zip.ZipInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiTest {

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

//    API文档
//    https://www.activiti.org/userguide/#api.services.start.processinstance

    /**
     * 部署流程
     */
    @Test
    public void deploy() {
        System.out.println(ioc.containsBean("processEngineConfiguration"));
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("processes/test-process.bpmn20.xml")
                .deploy();
    }

    @Test
    public void deployWithZip() {
        InputStream in=this.getClass().getClassLoader().getSystemResourceAsStream("processes/qjlc.zip");
        ZipInputStream zipInputStream=new ZipInputStream(in);
        Deployment deployment=processEngine.getRepositoryService()
                .createDeployment().addZipInputStream(zipInputStream)
                .name("请假流程WithZip")
                .deploy();
        System.out.println("流程部署ID:"+deployment.getId());
        System.out.println("流程部署Name:"+deployment.getName());

    }

    /*
     * 查询流程定义
     */
    @Test
    public void findProcessDefinition(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义查询
                /*指定查询条件,where条件*/
                //.deploymentId(deploymentId)//使用部署对象ID查询
                //.processDefinitionId(processDefinitionId)//使用流程定义ID查询
                //.processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
                //.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /*排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
                //.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                .list();//返回一个集合列表，封装流程定义
        //.singleResult();//返回唯一结果集
        //.count();//返回结果集数量
        //.listPage(firstResult, maxResults)//分页查询

        if(list != null && list.size()>0){
            for(ProcessDefinition processDefinition:list){
                System.out.println("流程定义ID:"+processDefinition.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义名称:"+processDefinition.getName());//对应HelloWorld.bpmn文件中的name属性值
                System.out.println("流程定义的key:"+processDefinition.getKey());//对应HelloWorld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:"+processDefinition.getVersion());//当流程定义的key值相同的情况下，版本升级，默认从1开始
                System.out.println("资源名称bpmn文件:"+processDefinition.getResourceName());
                System.out.println("资源名称png文件:"+processDefinition.getDiagramResourceName());
                System.out.println("部署对象ID:"+processDefinition.getDeploymentId());
                System.out.println("################################");
            }
        }

    }

    /**
     * 判断流程是否结束
     */
    @Test
    public void isEnd() {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId("150077").singleResult();
        if (processInstance==null) {
            System.out.println("流程结束");
        }else {
            System.out.println("流程未结束");
        }
    }


    /**
     * 获取下一个节点信息
     */
    @Test
    public void getNext() {

        BpmnModel model = repositoryService.getBpmnModel("test_any_node:1:102511");
        Collection<FlowElement> flowElementList = model.getMainProcess().getFlowElements();
        System.out.println(flowElementList);


//        ExecutionEntity execution = (ExecutionEntity) runtimeService
//                .createProcessInstanceQuery()
//                .processInstanceId("111")
//                .singleResult();
//
//        String activitiId = execution.getActivityId();
//
//
//
//
//        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)rs).getDeployedProcessDefinition("task_def_id");
//

    }



    /**
     * 删除已经部署的流程
     */
    @Test
    public void delete() {
        String[] ids = {"292501"};
        for (int i =0;i<ids.length;i++) {
            processEngine.getRepositoryService()
                    .deleteDeployment(ids[i],true);
        }


    }

    /**
     * 启动流程
     */
    @Test
    public void start() {
        System.out.println("【Before】Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
        Map<String,Object> variables = new HashMap<>();
        variables.put("assignee","aaa");
        processEngine.getRuntimeService()
                .startProcessInstanceByKey("test-process-01",variables); // 流程实例id传act_re_procdef.KEY
        System.out.println("【After】Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
    }

    /**
     * 获取流程变量
     */
    @Test
    public void getVariables() {
        Map<String,Object> variables = taskService.getVariables("100030");
        System.out.println(variables);


    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        processEngine.getTaskService().complete("85007");

    }

    /**
     * 完成任务
     * 带流程变量
     */
    @Test
    public void completeTask2() {
        String post = "cwkzy"; // 财务科职员
        String assignee = "";
        Map<String,Object> variables = new HashMap<>();
        variables.put("post","zy");
        variables.put("days",18);
        if ("cwkzy".equals(post)) assignee = "财务科科长";
        if ("xxkzy".equals(post)) assignee = "信息科科长";
        variables.put("assignee",assignee);
        processEngine.getTaskService().complete("70005",variables);

    }


    /**
     * 中止流程
     */
    @Test
    public void endProcess() {
        runtimeService.deleteProcessInstance("165001","delete");

    }

    /**
     * 查询待办任务
     */
    @Test
    public void queryWorkTodo() {
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("008").list();
        System.out.println(taskList);

    }

    @Test
    public void queryWorkDone() {
        List<HistoricTaskInstance> hisTaskList = historyService.createHistoricTaskInstanceQuery().taskAssignee("008").orderByTaskId().desc().list();
        System.out.println(hisTaskList);
    }

    /**
     * 获取流程图
     * 解决中文方框问题
     * @throws Exception
     */
    @Test
    public void genPic() throws Exception {
//        ProcessInstance pi = this.processEngine.getRuntimeService().createProcessInstanceQuery()
//                .processInstanceId(procId).singleResult();
        BpmnModel bpmnModel = this.processEngine.getRepositoryService().getBpmnModel("myProcess_1:1:105004");
        System.out.println(bpmnModel);
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", new ArrayList<>(),new ArrayList<>(),"宋体","宋体","宋体",null,1.0);
        FileOutputStream outputStream=new FileOutputStream("D:/a.png");
        byte[] b=new byte[1024];
        int red=inputStream.read(b);
        while(red!=-1){
            outputStream.write(b,0,red);
            red=inputStream.read(b);
        }
        outputStream.write(b);
        inputStream.close();
        outputStream.close();
    }


    @Test
    public void getPrevious() {
        String processInstanceId = "217532";
        List<HistoricTaskInstance> list = historyService//与历史数据（历史表）相关的service
                .createHistoricTaskInstanceQuery()//创建历史任务实例查询
                .processInstanceId(processInstanceId)
                .list();
        if (list.size()>1) { // 已经审批过一次了
            HistoricTaskInstance perviousTask = list.get(list.size()-2);
            System.out.println(list.get(list.size()-2));

        }else { // 第一个节点

        }


    }




}
