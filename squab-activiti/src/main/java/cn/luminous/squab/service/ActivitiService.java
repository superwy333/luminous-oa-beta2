package cn.luminous.squab.service;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.model.OaTaskModel;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface ActivitiService {

    ProcessInstance startProcess(String processKey, Map<String,Object> variables) throws Exception;

    Map<String,Object> getVariables(String actTaskId) throws Exception;

    void completeTask(String actTaskId) throws Exception;

    void completeTask(String actTaskId, Map<String,Object> variables) throws Exception;

    Boolean isEnd(String actTaskId);




}
