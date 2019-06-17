package cn.luminous.squab.controller.auth;


import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.model.DepartmentModel;
import cn.luminous.squab.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/department")
@Slf4j
public class DeptmentsController {

    @Autowired
    private DepartmentService departmentService;

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
            Map<String, Object> condition = new HashMap<>();
            if (rq.getPage() != null) {
                condition.put("page", (rq.getPage() - 1) * rq.getLimit());
                condition.put("limit", rq.getLimit());
            }
            Map<String, String> data = (Map<String, String>) rq.getData();
            if (data != null) {
                condition.putAll(data);
            }
            departmentModelList = departmentService.queryDepartmentsPage(condition);
            condition.put("page", null);
            condition.put("limit", null);
            queryCount = departmentService.queryDepartmentsPage(condition).size();
        } catch (Exception e) {
            R.nok(e.getMessage());
        }
        return R.ok(departmentModelList, queryCount);
    }


}
