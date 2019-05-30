package cn.luminous.squab.service.impl;

import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.entity.Department;
import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.mapper.SysUserMapper;
import cn.luminous.squab.model.SysUserModel;
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
                if (department.getPid()==0) {
                    staffId = Long.valueOf(department.getLeader());
                }else {
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
}
