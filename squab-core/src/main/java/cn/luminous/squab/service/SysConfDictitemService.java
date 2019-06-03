package cn.luminous.squab.service;

import cn.luminous.squab.entity.SysConfDictitem;

import java.util.List;

public interface SysConfDictitemService extends BaseService<SysConfDictitem> {

    List<SysConfDictitem> queryByParentCode(String parentCode);

    SysConfDictitem queryByCode(String parentCode, String itemCode);



}
