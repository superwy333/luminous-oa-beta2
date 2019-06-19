package cn.luminous.squab.controller.auth;

import cn.luminous.squab.controller.BaseController;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.model.SysUserModel;
import cn.luminous.squab.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sysUser")
@Slf4j
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/sysUserList")
    public ModelAndView toSysUserList() {
        ModelAndView m = new ModelAndView();
        m.setViewName("sysUser-list");
        return m;
    }

    @PostMapping("/sysUserList")
    @ResponseBody
    public String sysUserList(@RequestBody Rq rq) {
        List<SysUserModel> sysUserModelList = null;
        Integer queryCount = null;
        try {
            Map<String, Object> condition = parseListQueryCondition(rq);
            sysUserModelList = sysUserService.querySysUserPage(condition);
            condition.put("page", null);
            condition.put("limit", null);
            queryCount = sysUserService.querySysUserPage(condition).size();
        } catch (Exception e) {
            log.error("职员列表查询异常：", e);
            R.nok(e.getMessage());
        }
        return R.ok(sysUserModelList, queryCount);
    }


}
