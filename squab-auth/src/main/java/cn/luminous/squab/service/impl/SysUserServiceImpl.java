package cn.luminous.squab.service.impl;

import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.entity.BizMapping;
import cn.luminous.squab.entity.BizMappingAuth;
import cn.luminous.squab.entity.Department;
import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.mapper.SysUserMapper;
import cn.luminous.squab.model.SysUserModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private BizMappingAuthService bizMappingAuthService;

    @Autowired
    private BizMappingService bizMappingService;

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
        sysUer1.setUserCode(src);
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
    public void parseVariables(String userCode, Map<String, Object> variables, String type) throws Exception {
        SysUserModel sysUserModel = this.queryUserInfo(userCode);
        variables.put("post", sysUserModel.getPostName());
        variables.put("dept", sysUserModel.getDeptName());

        // 申请人 职代人 申请时组装
        if (Constant.PARSE_USERINFO_TYPE.APPLY.equals(type)) {
            // 下面的人在普通申请的时候是申请人，在职代的时候是职代人
            variables.put("sqr", sysUserModel.getName());
            variables.put("sqrCode", sysUserModel.getUserCode());
//            variables.put("zdr", sysUserModel.getName());
//            variables.put("zdrCode", sysUserModel.getUserCode());
        }



        // 当前主办人
        variables.put("operator", sysUserModel.getName());
        variables.put("operatorCode", sysUserModel.getUserCode());

        // 是否是部门直属人员
        Department department = departmentService.queryDepartment(sysUserModel.getUserCode());
        if (department.getPid() == 0) {
            variables.put("bmzs", true);
        } else {
            variables.put("bmzs", false);
        }
        //  是否含有分管领导
        if (department.getLeaderBranch() != null) {
            variables.put("hasfgld", true);
        } else {
            variables.put("hasfgld", false);
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
        if (staffId == null) throw new Exception(department.getName() + "没有设置leader， 请联系管理员！");
        SysUer leader = queryBiStaffId(staffId);
        assignees.put("ks", leader.getUserCode());
        // 上级部门 sjbm
        if (department.getPid() == 0) {
            staffId = Long.valueOf(department.getLeader());
        } else {
            staffId = department.getParentLeader();
        }
        // 分管领导 fgld


        return null;
    }

    @Override
    public List<Map<String, String>> getProcessMenus(String userCode) throws Exception {
        SysUer sysUer = new SysUer();
        sysUer.setUserCode(userCode);
        sysUer = queryOne(sysUer);

        /**
         * type
         * 0-全部
         * 1-指定人
         * 2-指定部门
         * 3-指定岗位
         */

        // 找到所有人员都有的权限
        BizMappingAuth queryAll = new BizMappingAuth();
        queryAll.setType(Constant.BIZ_MAPPING_AUTH_TYPE.ALL);
        List<BizMappingAuth> queryAllList = bizMappingAuthService.query(queryAll);

        SysUserModel sysUserModel = queryUserInfo(sysUer.getUserCode());
        // 找到指定人员有的权限
        BizMappingAuth queryByUser = new BizMappingAuth();
        queryByUser.setType(Constant.BIZ_MAPPING_AUTH_TYPE.BY_USER);
        queryByUser.setUserId(sysUer.getId());
        List<BizMappingAuth> queryByUserList = bizMappingAuthService.query(queryByUser);
        // 找到这个人所属部门的权限
        BizMappingAuth queryByDept = new BizMappingAuth();
        queryByDept.setType(Constant.BIZ_MAPPING_AUTH_TYPE.BY_DEPT);
        queryByDept.setDeptId(sysUserModel.getDeptId());
        List<BizMappingAuth> queryByDeptList = bizMappingAuthService.query(queryByDept);

        // 找到这个人岗位的权限
        BizMappingAuth queryByPost = new BizMappingAuth();
        queryByPost.setType(Constant.BIZ_MAPPING_AUTH_TYPE.BY_POST);
        queryByPost.setPostId(sysUserModel.getPostId());
        List<BizMappingAuth> queryByPostList = bizMappingAuthService.query(queryByPost);

        List<Map<String, String>> bizKeyList = new ArrayList<>();
        parseBizList(queryAllList, bizKeyList);
        parseBizList(queryByUserList, bizKeyList);
        parseBizList(queryByDeptList, bizKeyList);
        parseBizList(queryByPostList, bizKeyList);


        return bizKeyList;
    }

    private void parseBizList(List<BizMappingAuth> src, List<Map<String, String>> bizKeyList) {
        src.stream().forEach(bizMappingAuth -> {
            BizMapping bizMapping = bizMappingService
                    .queryById(bizMappingAuth.getBizMappingId());
            Map<String, String> item = new HashMap<>();
            item.put("name", bizMapping.getBizName());
            item.put("url", "/workflow/apply?bizKey=" + bizMapping.getBizKey());
            bizKeyList.add(item);
        });
        // 去重
        removeDuplicate(bizKeyList);
    }

    private List removeDuplicate(List<Map<String, String>> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).get("url").equals(list.get(i).get("url"))) {
                    list.remove(j);
                }

            }
        }
        return list;
    }

}
