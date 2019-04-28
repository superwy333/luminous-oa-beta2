package cn.luminous.squabweb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkController {




    @RequestMapping("workingTask")
    public String toWorkingTaskList() {
        return "working-task";
    }

    @RequestMapping("applyList")
    public String toApplyList() {
        return "apply-list";

    }
}
