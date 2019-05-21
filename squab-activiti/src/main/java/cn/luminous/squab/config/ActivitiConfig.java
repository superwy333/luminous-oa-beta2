package cn.luminous.squab.config;

import cn.luminous.squab.listener.ApproveListener;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;


/**
 * 工作流配置
 * 放到配置文件中，这个不使用
 */
//@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {

    }

    @Bean
    public ApproveListener approveListener() {
        return new ApproveListener();
    }

//    @Value("${dataType}")
//    private String dataType;

//    /**
//     * spring 集成 activiti
//     */
//    @Bean
//    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
//        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
//        processEngineConfiguration.setDataSource(dataSource);
//        //表不存在创建表
//        processEngineConfiguration.setDatabaseSchemaUpdate("true");
//        //指定数据库
//        processEngineConfiguration.setDatabaseType("mysql");
//        processEngineConfiguration.setTransactionManager(transactionManager);
//        //历史变量
//        processEngineConfiguration.setHistory("full");
//        //指定字体
//        processEngineConfiguration.setActivityFontName("宋体");
//        processEngineConfiguration.setAnnotationFontName("宋体");
//        processEngineConfiguration.setLabelFontName("宋体");
//
//        processEngineConfiguration.setProcessDiagramGenerator(new DefaultProcessDiagramGenerator());
//
//        return processEngineConfiguration;
//    }

    //流程引擎，与spring整合使用factoryBean
//    @Bean
//    public ProcessEngineFactoryBean processEngine(ProcessEngineConfiguration processEngineConfiguration) {
//        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
//        processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
//        return processEngineFactoryBean;
//    }
//
//    @Bean
//    public RepositoryService repositoryService(ProcessEngine processEngine) {
//        return processEngine.getRepositoryService();
//    }
//
//    @Bean
//    public RuntimeService runtimeService(ProcessEngine processEngine) {
//        return processEngine.getRuntimeService();
//    }
//
//    @Bean
//    public TaskService taskService(ProcessEngine processEngine) {
//        return processEngine.getTaskService();
//    }
//
//    @Bean
//    public HistoryService historyService(ProcessEngine processEngine) {
//        return processEngine.getHistoryService();
//    }
//
//    @Bean
//    public FormService formService(ProcessEngine processEngine) {
//        return processEngine.getFormService();
//    }
//
//    @Bean
//    public IdentityService identityService(ProcessEngine processEngine) {
//        return processEngine.getIdentityService();
//    }
//
//    @Bean
//    public ManagementService managementService(ProcessEngine processEngine) {
//        return processEngine.getManagementService();
//    }
//
//    @Bean
//    public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine) {
//        return processEngine.getDynamicBpmnService();
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper();
//    }
}
