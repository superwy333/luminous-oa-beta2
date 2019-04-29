package cn.luminous.squab.service;

import org.activiti.engine.runtime.ProcessInstance;

import java.util.Map;

public interface ActivitiService {

    ProcessInstance startProcess(String processKey, Map<String,Object> variables) throws Exception;




}
