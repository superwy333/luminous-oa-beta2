package cn.luminous.squab.service;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.model.OaTaskModel;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ActivitiService {

    ProcessInstance startProcess(String processKey, Map<String,Object> variables) throws Exception;

    Map<String,Object> getVariables(String actTaskId) throws Exception;

    void completeTask(String actTaskId) throws Exception;

    void completeTask(String actTaskId, Map<String,Object> variables) throws Exception;

    void deleteTask(String actTaskId) throws Exception;

    Boolean isEnd(String actTaskId) throws Exception;

    InputStream getDiagramOrgin(String deployId) throws Exception;

    List<HistoricTaskInstance> getCallBackNodes(String processInstanceId) throws Exception;

    void callBackTaskToHisTask(String hisTaskId) throws Exception;

    void modeDeploy(String bizKey, String modelId, Long formId) throws Exception;




}
