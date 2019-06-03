package cn.luminous.squab.service.impl;

import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.entity.Department;
import cn.luminous.squab.entity.SysConfDictitem;
import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.mapper.SysUserMapper;
import cn.luminous.squab.model.SysUserModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.DepartmentService;
import cn.luminous.squab.service.SysConfDictitemService;
import cn.luminous.squab.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUer> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SysConfDictitemService sysConfDictitemService;

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
        SysUer sysUer1 = new SysUer();
        sysUer1.setName(src);
        sysUer1 = queryOne(sysUer1);
        // 先查询组织架构树
        Department department = departmentService.queryDepartment(sysUer1.getUserCode());
        String nextAssignee;
        Long staffId;
        if (department == null) throw new Exception("当前申请人无部门，请联系管理员");
        switch (type) {
            case Constant.ASSIGNEE_KEY.KS:
                staffId = Long.valueOf(department.getLeader());
                break;
            case Constant.ASSIGNEE_KEY.SJBM:
                if (department.getPid() == 0) {
                    staffId = Long.valueOf(department.getLeader());
                } else {
                    staffId = department.getParentLeader();
                }
                break;
            case Constant.ASSIGNEE_KEY.FGLD:
                staffId = Long.valueOf(department.getLeaderBranch());
                break;
            case Constant.ASSIGNEE_KEY.RSBA:
                staffId = 24L;
                break;
            case Constant.ASSIGNEE_KEY.ZJL:
                staffId = 2L;
                break;
            case Constant.ASSIGNEE_KEY.CW:
                staffId = 6L;
                break;
            case Constant.ASSIGNEE_KEY.CN:
                staffId = 10L;
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

    @Override
    public SysUserModel queryUserInfo(String userCode) throws Exception {
        return sysUserMapper.queryUserInfo(userCode);
    }

    @Override
    public SysUer queryBiStaffId(Long staffId) {
        SysUer sysUer = new SysUer();
        sysUer.setStaffId(staffId);
        return queryOne(sysUer);
    }

    @Override
    public void parseVariables(String userName, Map<String, Object> variables) throws Exception{
        SysUserModel sysUserModel = this.queryUserInfo(userName);
        variables.put("post",sysUserModel.getPostName());
        variables.put("dept",sysUserModel.getDeptName());
        // 当前主办人
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        variables.put("sqr",username);
        // 是否是部门直属人员
        Department department = departmentService.queryDepartment(sysUserModel.getUserCode());
        if (department.getPid()==0) {
            variables.put("bmzs",true);
        }else {
            variables.put("bmzs",false);
        }
        //  是否含有分管领导
        if (department.getLeaderBranch()!=null) {
            variables.put("hasfgld",true);
        }else {
            variables.put("hasfgld",false);
        }
    }

    /**
     * 根据userCode和用户所在科室以及部门，找出用户的组织架构树
     *
     * @param userCode
     * @return 用以加入流程变量中的一个map
     * @throws Exception
     */
    @Override
    public Map<String, String> getAssigneesByDeptAndPost(String userCode) throws Exception {
        Map<String, String> assignees = new HashMap<>();
        Long staffId;
//        List<SysConfDictitem> sysConfDictitemList = sysConfDictitemService.queryByParentCode(Constant.SYS_DICT_CODE.DYNAMIC_APPROVER);
//        sysConfDictitemList.stream().forEach(sysConfDictitem -> {
//            setAssignees(sysConfDictitem.getDicItemCode(), assignees);
//        });
        Department department = departmentService.queryDepartment(userCode);
        // 需要进行动态设置负责人的节点
        // 科室 ks
        staffId = Long.valueOf(department.getLeader());
        if (staffId==null) throw new Exception(department.getName() + "没有设置leader， 请联系管理员！");
        SysUer leader = queryBiStaffId(staffId);
        assignees.put("ks",leader.getUserCode());
        // 上级部门 sjbm
        if (department.getPid() == 0) {
            staffId = Long.valueOf(department.getLeader());
        } else {
            staffId = department.getParentLeader();
        }
        // 分管领导 fgld


        return null;
    }

//    private void setAssignees(String type, Map<String, String> assignees) {
//        switch (type) {
//            case Constant.ASSIGNEE_KEY.KS:
//                staffId = Long.valueOf(department.getLeader());
//                break;
//            case Constant.ASSIGNEE_KEY.SJBM:
//                if (department.getPid() == 0) {
//                    staffId = Long.valueOf(department.getLeader());
//                } else {
//                    staffId = department.getParentLeader();
//                }
//                break;
//            case Constant.ASSIGNEE_KEY.FGLD:
//                break;
//            case Constant.ASSIGNEE_KEY.RSBA:
//                break;
//            case Constant.ASSIGNEE_KEY.ZJL:
//                break;
//            case Constant.ASSIGNEE_KEY.CW:
//                break;
//            case Constant.ASSIGNEE_KEY.CN:
//                break;
//            case Constant.ASSIGNEE_KEY.CGGLB:
//                break;
//            default:
//                break;
//        }
//
//    }

}
