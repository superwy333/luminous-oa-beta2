package cn.luminous.squab.controller.activiti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {


    @RequestMapping("/editor")
    public String test(){
        return "modeler";
    }
}
