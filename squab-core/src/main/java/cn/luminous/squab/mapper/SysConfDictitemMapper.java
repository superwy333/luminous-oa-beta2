package cn.luminous.squab.mapper;

import cn.luminous.squab.entity.SysConfDictitem;
import cn.luminous.squab.mybatis.imapper.IMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysConfDictitemMapper extends IMapper<SysConfDictitem> {


    List<SysConfDictitem> queryByParentCode(@Param("parentCode") String parentCode);


    SysConfDictitem queryByCode(@Param("parentCode") String parentCode, @Param("itemCode") String itemCode);




}