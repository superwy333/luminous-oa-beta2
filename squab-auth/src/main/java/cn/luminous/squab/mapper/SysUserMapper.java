package cn.luminous.squab.mapper;

import cn.luminous.squab.entity.Department;
import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.model.SysUserModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends IMapper<SysUer> {


    List<SysUer> queryAllUsers(@Param(value = "sysUer") SysUer sysUer);

    //Department queryDepartment(@Param(value = "userCode") String userCode);

    SysUserModel queryUserInfo(@Param(value = "userCode") String userCode);

    List<SysUserModel> queryUserPage(@Param("condition") Map<String,Object> condition);

}
