package cn.luminous.squab.mapper;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OaTaskMapper extends IMapper<OaTask> {


    List<OaTaskModel> queryTaskToDo(@Param("assignee") String assignee);

    List<OaTaskModel> queryTaskDone(@Param("assignee") String assignee);

    OaTaskModel queryTaskById(@Param("taskId") String taskId);



}
