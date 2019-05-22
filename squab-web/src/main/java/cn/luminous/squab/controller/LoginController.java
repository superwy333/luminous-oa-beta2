package cn.luminous.squab.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class LoginController {

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
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            log.error("登陆失败:" + e.getCause().getMessage());
            model.addAttribute("msg",e.getCause().getMessage());
            return "/login";
        }
        return "redirect:/index";
    }



}
