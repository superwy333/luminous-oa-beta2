package cn.luminous.squab.service;

import cn.luminous.squab.entity.Department;

public interface DepartmentService extends BaseService<Department> {

    Department queryDepartment(String userCode);
}
