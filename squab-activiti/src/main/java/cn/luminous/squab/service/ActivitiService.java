package cn.luminous.squab.service;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface ActivitiService {

    ProcessInstance startProcess(String processKey, Map<String,Object> variables) throws Exception;

    List<Task> queryTaskTodo(String userCode) throws Exception;




}
