package cn.luminous.squab.methods;


import cn.luminous.squab.entity.Department;
import cn.luminous.squab.service.DepartmentService;
import cn.luminous.squab.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("conditionMethods")
public class ConditionMethods {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 当前主办人的部门是否有分管领导
     * @param deptId
     * @return
     */
    public boolean deptHasFgld(Long deptId) {
        Department department = departmentService.queryById(deptId);
        return !department.getLeaderBranch().isEmpty();
    }


    /**
     * 当前主办人是否是部门直属人员
     * @param userId
     * @return
     */
    public boolean isDeptZs(Long userId) {
        Department department = departmentService.queryDepartmentByUserId(userId);
        return (department.getPid()==0);
    }

    /**
     * 当前主办人是否是自己本部门的领导
     * 针对部门只有副职的情况下判断使用
     * @param userId
     * @return
     */
    public boolean isDeptLeader(Long userId) {
        Department department = departmentService.queryDepartmentByUserId(userId);
        return (department.getLeader().equals(String.valueOf(userId)));

    }
}
