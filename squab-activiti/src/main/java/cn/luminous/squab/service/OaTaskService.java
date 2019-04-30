package cn.luminous.squab.service;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.model.OaTaskModel;

import java.util.List;

public interface OaTaskService extends BaseService<OaTask> {


    R registerTask(OaTask oaTask) throws Exception;

    List<OaTaskModel> queryTaskToDo() throws Exception;


}
