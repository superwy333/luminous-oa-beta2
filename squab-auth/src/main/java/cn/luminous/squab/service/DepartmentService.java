package cn.luminous.squab.service;

import cn.luminous.squab.entity.Department;
import cn.luminous.squab.model.DepartmentModel;

import java.util.List;
import java.util.Map;

public interface DepartmentService extends BaseService<Department> {

    Department queryDepartment(String userCode);

    List<DepartmentModel> queryDepartmentsPage(Map condition);
}
