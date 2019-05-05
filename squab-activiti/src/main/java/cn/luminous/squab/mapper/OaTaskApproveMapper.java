package cn.luminous.squab.mapper;

import cn.luminous.squab.entity.OaTaskApprove;
import cn.luminous.squab.model.OaTaskApproveModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OaTaskApproveMapper extends IMapper<OaTaskApprove> {

    List<OaTaskApproveModel> queryApproveDetail(@Param("oaTaskId") Long oaTaskId);

}
