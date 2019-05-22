package cn.luminous.squab.service.impl;

import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.entity.Department;
import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.mapper.SysUserMapper;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.DepartmentService;
import cn.luminous.squab.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUer> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private DepartmentService departmentService;

    @Override
    protected IMapper<SysUer> getBaseMapper() {
        return this.sysUserMapper;
    }

    @Override
    public List<SysUer> queryAllUsers(SysUer sysUer) {
        return sysUserMapper.queryAllUsers(sysUer);
    }


    /**
     * 通过src和type查询下一个审批人
     *
     * @param src
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public String getAssignee(String src, String type) throws Exception {
        // 先查询组织架构树
        Department department = departmentService.queryDepartment(src);
        String nextAssignee;
        Long staffId;
        if (department == null) throw new Exception("当前申请人无部门，请联系管理员");
        switch (type) {
            case Constant.ASSIGNEE_KEY.KS:
                staffId = Long.valueOf(department.getLeader());
                break;
            case Constant.ASSIGNEE_KEY.SJBM:
                staffId = department.getParentLeader();
                break;
            case Constant.ASSIGNEE_KEY.FGLD:
                staffId = Long.valueOf(department.getLeaderBranch());
                break;
            case Constant.ASSIGNEE_KEY.RSBA:
                staffId = 3L;
                break;
            case Constant.ASSIGNEE_KEY.ZJL:
                staffId = 2L;
                break;
            default:
                staffId = 1L;
                break;
        }
        SysUer sysUer = new SysUer();
        sysUer.setStaffId(staffId);
        sysUer = this.queryOne(sysUer);
        return sysUer.getUserCode();
    }


//    private String getLeader(Department department) throws Exception{
//        String leaderId = department.getLeader();
//        if (leaderId!=null) {
//            SysUer sysUer = new SysUer();
//            sysUer.setStaffId(Long.valueOf(leaderId));
//            sysUer = this.queryOne(sysUer);
//            return sysUer.getUserCode();
//        }else {
//            Department father = getDepartmentDegree(department);
//            SysUer sysUer = new SysUer();
//            sysUer.setStaffId(Long.valueOf(father.getLeader()));
//            sysUer = this.queryOne(sysUer);
//            return sysUer.getUserCode();
//        }
//    }
//
//    private Department getDepartmentDegree(Department department) throws Exception {
//        Integer pid = department.getPid();
//        Department departmentInDB = departmentService.queryById(Long.valueOf(pid));
//        if (departmentInDB.getLeader()==null) {
//            return getDepartmentDegree(departmentInDB); // 递归
//        }else {
//            return departmentInDB;
//        }
//    }
//
//    /**
//     * 获取当前部门的上级部门，即有leader的部门
//     * 如果当前部门有leader，则直接获取当前部门
//     * @param department
//     * @return
//     * @throws Exception
//     */
//    private Department getFather(Department department) throws Exception{
//        if (department.getLeader()==null) {
//            return getDepartmentDegree(department);
//        }else {
//            return department;
//        }
//    }

}
