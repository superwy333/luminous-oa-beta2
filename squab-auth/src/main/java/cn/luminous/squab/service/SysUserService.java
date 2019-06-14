package cn.luminous.squab.service;

import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.model.SysUserModel;

import java.util.List;
import java.util.Map;

public interface SysUserService extends BaseService<SysUer> {

    List<SysUer> queryAllUsers(SysUer sysUer);

    String getAssignee(String src, String type) throws Exception;

    SysUserModel queryUserInfo(String userCode) throws Exception;

    Map<String,String> getAssigneesByDeptAndPost(String userCode) throws Exception;

    SysUer queryBiStaffId(Long staffId);

    void parseVariables(String userCode, Map<String,Object> variables, String type) throws Exception;

    List<Map<String,String>> getProcessMenus(String userCode) throws Exception;

}
