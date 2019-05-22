package cn.luminous.squab.service;

import cn.luminous.squab.entity.SysUer;

import java.util.List;

public interface SysUserService extends BaseService<SysUer> {

    List<SysUer> queryAllUsers(SysUer sysUer);

    String getAssignee(String src, String type) throws Exception;

}
