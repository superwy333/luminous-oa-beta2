package cn.luminous.squab.controller;


import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.model.MenuModel;
import cn.luminous.squab.model.SysUserModel;
import cn.luminous.squab.service.SysUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/")
    public String toLogin() {
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return "login";
        }
        return "redirect:/index"; // 若已经登录，则直接跳转工作台
    }

    @RequestMapping(value = "/loginout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "/login";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        try {
            SysUer currentUser = (SysUer) SecurityUtils.getSubject().getPrincipal();
            SysUer sysUer = new SysUer();
            sysUer.setUserCode(currentUser.getUserCode());
            SysUserModel sysUserModel = sysUserService.queryUserInfo(currentUser.getUserCode());
            model.addAttribute("username", sysUserModel.getName());
            model.addAttribute("userCode", sysUserModel.getUserCode());
            model.addAttribute("post", sysUserModel.getPostName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("loginType") String loginType,
                          Model model) {
        SysUer sysUer = new SysUer();
        // 判断是使用姓名登陆还是使用工号登陆
        if (Constant.LOGIN_TYPE.USER_NAME.equals(loginType)) { // 使用姓名登陆
            SysUer queryByUserName = new SysUer();
            queryByUserName.setName(username);
            List<SysUer> sysUerList = sysUserService.query(queryByUserName);
            if (sysUerList.size() == 0) {
                model.addAttribute("msg", "用户不存在");
                return "/login";
            } else if (sysUerList.size() > 1) {
                model.addAttribute("msg", "查询到重名用户，请联系管理员或使用工号登陆！");
                return "/login";
            } else {
                sysUer = sysUerList.get(0);
            }

        } else if (Constant.LOGIN_TYPE.USER_CODE.equals(loginType)) { // 使用工号登陆
            SysUer queryByUserCode = new SysUer();
            queryByUserCode.setUserCode(username);
            sysUer = sysUserService.queryOne(queryByUserCode);
            if (sysUer == null) {
                model.addAttribute("msg", "用户不存在");
                return "/login";
            }
        }

        UsernamePasswordToken token = new UsernamePasswordToken(sysUer.getUserCode(), password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            log.error("登陆失败:" + e.getCause().getMessage());
            model.addAttribute("msg", e.getCause().getMessage());
            return "/login";
        }
        return "redirect:/index";
    }

    @PostMapping("/menu")
    @ResponseBody
    public String menu(@RequestParam("userCode") String userCode) {
        Map<String, Object> menu = new HashMap<>();
        List<MenuModel> result = new ArrayList<>();

        Gson gson = new GsonBuilder().create();
        try {
            // 我的工作台是每人都有的
            MenuModel menuModel = new MenuModel();
            menuModel.setName("我的工作台");
            Map<String, String> myTask = new HashMap<>();
            Map<String, String> todoTask = new HashMap<>();
            myTask.put("name", "我发起的");
            myTask.put("url", "/workflow/myTaskList");
            todoTask.put("name", "我的待办");
            todoTask.put("url", "/workflow/taskTodoList");
            List<Map<String, String>> child = new ArrayList<>();
            child.add(myTask);
            child.add(todoTask);
            menuModel.setChild(child);
            result.add(menuModel);

            // 获取当前登陆人
            SysUer currentUser = (SysUer) SecurityUtils.getSubject().getPrincipal();

            // 填报申请
            MenuModel applyMenu = new MenuModel();
            applyMenu.setName("填报申请");
            applyMenu.setChild(sysUserService.getProcessMenus(currentUser.getUserCode()));
            result.add(applyMenu);

            // 管理员权限
            // TODO 如果是管理员，则加上下面两个菜单
            MenuModel form = new MenuModel();
            form.setName("表单管理");
            Map<String, String> formItem = new HashMap<>();
            formItem.put("name", "表单管理");
            formItem.put("url", "/dynamicForm/formList");
            form.getChild().add(formItem);
            result.add(form);
            MenuModel process = new MenuModel();
            process.setName("流程管理");
            Map<String, String> processItem1 = new HashMap<>();
            processItem1.put("name", "模型管理");
            processItem1.put("url", "/modeler/modelList");
            Map<String, String> processItem2 = new HashMap<>();
            processItem2.put("name", "部署管理");
            processItem2.put("url", "/deploy/deployList");
            process.getChild().add(processItem1);
            process.getChild().add(processItem2);
            result.add(process);
        } catch (Exception e) {
            log.error("菜单获取失败,失败原因：" + e);
            R.nok("菜单获取失败，请联系管理管！" + e.getMessage());
        }
        menu.put("success", true);
        menu.put("code", "200");
        menu.put("result", result);
        return gson.toJson(menu);
    }


}
