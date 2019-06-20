package cn.luminous.squab.service;

import cn.luminous.squab.entity.OaTaskApprove;
import cn.luminous.squab.model.OaTaskApproveModel;

import java.util.List;


public interface OaTaskApproveService extends BaseService<OaTaskApprove> {

    List<OaTaskApproveModel> queryOaTaskApproveForPrint(Long oaTaskId) throws Exception;





}
