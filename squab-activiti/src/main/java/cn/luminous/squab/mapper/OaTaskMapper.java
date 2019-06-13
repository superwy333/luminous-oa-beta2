package cn.luminous.squab.mapper;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OaTaskMapper extends IMapper<OaTask> {


    List<OaTaskModel> queryTaskToDo(@Param("assignee") String assignee);

    List<OaTaskModel> queryTaskToDoPage(@Param("condition") Map<String,Object> condition);

    List<OaTaskModel> queryTaskDone(@Param("assignee") String assignee);

    List<OaTaskModel> queryTaskDonePage(@Param("condition") Map<String,Object> condition);

    OaTaskModel queryTaskById(@Param("taskId") String taskId);

//    List<OaTaskModel> queryMyTask(@Param("userCode") String userCode,
//                                  @Param("page") Integer page,
//                                  @Param("limit") Integer limit);

    List<OaTaskModel> queryMyTask(@Param("condition") Map<String,Object> condition);

    String callTaskNo(Map map);



}
