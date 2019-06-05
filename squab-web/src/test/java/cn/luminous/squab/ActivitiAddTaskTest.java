package cn.luminous.squab;


import org.activiti.engine.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 加签测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiAddTaskTest {

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
    public void test() {

        String taskId = "442541";
        String assignee = "加签测试";


        AddTaskCmd addTaskCmd = new AddTaskCmd(taskId,
                assignee,
                runtimeService,
                taskService,
                repositoryService,
                false);
        managementService.executeCommand(addTaskCmd);

    }
}
