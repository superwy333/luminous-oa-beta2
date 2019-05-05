package cn.luminous.squab.service;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.OaTaskApprove;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.model.OaTaskModel;

import java.util.List;

public interface OaTaskService extends BaseService<OaTask> {


    String registerTask(OaTask oaTask) throws Exception;

    String approveTask(OaTaskApprove oaTaskApprove) throws Exception;

    String rejectTask(OaTaskApprove oaTaskApprove) throws Exception;

    List<OaTaskModel> queryTaskToDo() throws Exception;

    List<OaTaskModel> queryTaskDone() throws Exception;

    OaTaskModel queryTaskById(String taskId) throws Exception;


}
