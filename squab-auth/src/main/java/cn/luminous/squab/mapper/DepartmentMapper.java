package cn.luminous.squab.mapper;

import cn.luminous.squab.entity.Department;
import cn.luminous.squab.mybatis.imapper.IMapper;
import org.apache.ibatis.annotations.Param;

public interface DepartmentMapper extends IMapper<Department> {

    Department queryDepartment(@Param(value = "userCode") String userCode);
}