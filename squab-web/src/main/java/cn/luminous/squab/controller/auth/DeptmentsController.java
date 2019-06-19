package cn.luminous.squab.controller.auth;


import cn.luminous.squab.controller.BaseController;
import cn.luminous.squab.entity.Department;
import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.model.DepartmentModel;
import cn.luminous.squab.service.DepartmentService;
import cn.luminous.squab.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/department")
@Slf4j
public class DeptmentsController extends BaseController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SysUserService sysUserService;


    @GetMapping("/edit")
    public ModelAndView edit(Model model, @RequestParam("id") Long id) {
        ModelAndView m = new ModelAndView();
        try {
            Department department = departmentService.queryById(id);
            List<SysUer> sysUerList = sysUserService.query(new SysUer());
            List<Department> departmentList = departmentService.query(new Department());
            model.addAttribute("department", department);
            model.addAttribute("sysUerList", sysUerList);
            model.addAttribute("departmentList", departmentList);
        } catch (Exception e) {
            log.error("查询部门信息失败: ", e);
        }
        m.setViewName("dept-edit");
        return m;
    }

    @PostMapping("/edit")
    @ResponseBody
    public String edit(@RequestBody Rq rq) {
        try {
            Map<String, Object> data = (Map<String, Object>) rq.getData();
            String newLeader = (String) data.get("leader");
            String newLeaderBranch = (String) data.get("leaderBranch");
            Integer pid = null;
            if (!data.get("pid").equals("")) {
                pid = Integer.valueOf((String) data.get("pid"));
            }
            Long id = Long.valueOf((String) data.get("id"));
            Department department = departmentService.queryById(id);
            department.setLeader(newLeader);
            department.setLeaderBranch(newLeaderBranch);
            if (pid != null && department.getPid() != pid) {
                department.setPid(pid);
                Department parentDept = departmentService.queryById(Long.valueOf(pid));
                department.setParentLeader(Long.valueOf(parentDept.getLeader()));
            }
            if (pid == null) {
                department.setPid(0);
                department.setParentLeader(null);
            }
            departmentService.updateById(department);
        } catch (Exception e) {
            log.error("修改部门数据失败{}", e);
            return R.nok(e.getMessage());
        }
        return R.ok();
    }


    @GetMapping("/deptList")
    public ModelAndView toDeptList() {
        ModelAndView m = new ModelAndView();
        m.setViewName("deptments-list");
        return m;
    }

    @PostMapping("/deptList")
    @ResponseBody
    public String deptList(@RequestBody Rq rq) {
        List<DepartmentModel> departmentModelList = null;
        Integer queryCount = null;
        try {
            Map<String, Object> condition = parseListQueryCondition(rq);
            departmentModelList = departmentService.queryDepartmentsPage(condition);
            condition.put("page", null);
            condition.put("limit", null);
            queryCount = departmentService.queryDepartmentsPage(condition).size();
            departmentModelList.stream().forEach(departmentModel -> {
                SysUer sysUer = new SysUer();
                SysUer query = new SysUer();
                if (departmentModel.getLeader() != null) {
                    query.setStaffId(Long.valueOf(departmentModel.getLeader()));
                    sysUer = sysUserService.queryOne(query);
                    departmentModel.setLeaderTxt(sysUer.getName());
                }

                if (departmentModel.getParentLeader() != null) {
                    query.setStaffId(Long.valueOf(departmentModel.getParentLeader()));
                    sysUer = sysUserService.queryOne(query);
                    departmentModel.setParentLeaderTxt(sysUer.getName());
                }


                if (departmentModel.getLeaderBranch() != null) {
                    query.setStaffId(Long.valueOf(departmentModel.getLeaderBranch()));
                    sysUer = sysUserService.queryOne(query);
                    departmentModel.setLeaderBranchTxt(sysUer.getName());
                }

                Department department = departmentService.queryById(Long.valueOf(departmentModel.getPid()));
                if (department != null) {
                    departmentModel.setParentDeptTxt(department.getName());
                }

            });
        } catch (Exception e) {
            log.error("部门列表查询异常：", e);
            R.nok(e.getMessage());
        }
        return R.ok(departmentModelList, queryCount);
    }


}
