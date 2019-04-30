package cn.luminous.squab.service;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.http.R;
import org.activiti.engine.task.Task;

import java.util.List;

public interface OaTaskService extends BaseService<OaTask> {


    R registerTask(OaTask oaTask) throws Exception;

    List<Task> queryTaskToDo() throws Exception;


}
