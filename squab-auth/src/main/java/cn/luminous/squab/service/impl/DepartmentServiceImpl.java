package cn.luminous.squab.service.impl;

import cn.luminous.squab.entity.Department;
import cn.luminous.squab.mapper.DepartmentMapper;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("departmentService")
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    protected IMapper<Department> getBaseMapper() {
        return this.departmentMapper;
    }

    @Override
    public Department queryDepartment(String userCode) {
        return departmentMapper.queryDepartment(userCode);
    }
}
