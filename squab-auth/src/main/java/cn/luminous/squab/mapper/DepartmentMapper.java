package cn.luminous.squab.mapper;

import cn.luminous.squab.entity.Department;
import cn.luminous.squab.model.DepartmentModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DepartmentMapper extends IMapper<Department> {

    Department queryDepartment(@Param(value = "userCode") String userCode);

    List<DepartmentModel> queryDepartmentsPage(@Param("condition") Map<String,Object> condition);


}