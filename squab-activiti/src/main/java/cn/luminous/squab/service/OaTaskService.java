package cn.luminous.squab.service;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.OaTaskApprove;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.model.OaTaskApproveModel;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.model.OaTaskNodeModel;

import java.util.List;

public interface OaTaskService extends BaseService<OaTask> {


    OaTask registerTask(OaTask oaTask) throws Exception; //  启动流程

    String approveTask(OaTaskApprove oaTaskApprove) throws Exception; // 审批通过，完成当前任务

    String rejectTask(OaTaskApprove oaTaskApprove) throws Exception; // 审批驳回，直接停止流程

    String canCelTask(OaTaskApprove oaTaskApprove) throws Exception; // 申请人撤回任务

    List<OaTaskModel> queryTaskToDo(String userCode) throws Exception; // 查询待办

    List<OaTaskModel> queryTaskToDoPage(String userCode, Integer page, Integer limit) throws Exception; // 查询待办

    List<OaTaskModel> queryTaskDone() throws Exception; // 查询已办

    OaTaskModel queryTaskById(String taskId) throws Exception;

    List<OaTaskApproveModel> queryTaskApproveDetails(Long oaTaskId) throws Exception;

    List<OaTaskModel> queryMyTaskPage(String userCode, Integer page, Integer limit) throws Exception;

    void testTransactional() throws Exception;

    List<OaTaskNodeModel> getCallBackNodes(String processInstanceId) throws Exception;

    void callBackTaskToHisTask(String hisTaskId) throws Exception;

    String getTaskNo() throws Exception;


}
