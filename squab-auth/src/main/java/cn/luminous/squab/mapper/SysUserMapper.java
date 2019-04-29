package cn.luminous.squab.mapper;

import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.mybatis.imapper.IMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends IMapper<SysUer> {


    List<SysUer> queryAllUsers(@Param(value = "sysUer") SysUer sysUer);

}
