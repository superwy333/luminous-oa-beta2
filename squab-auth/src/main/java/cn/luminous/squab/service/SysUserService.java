package cn.luminous.squab.service;

import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.model.SysUserModel;

import java.util.List;

public interface SysUserService extends BaseService<SysUer> {

    List<SysUer> queryAllUsers(SysUer sysUer);

    String getAssignee(String src, String type) throws Exception;

    SysUserModel queryUserInfo(String userCode) throws Exception;

}
