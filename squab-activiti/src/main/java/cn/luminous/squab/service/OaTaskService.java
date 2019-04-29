package cn.luminous.squab.service;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.http.R;

public interface OaTaskService extends BaseService<OaTask> {


    R registerTask(OaTask oaTask) throws Exception;


}
