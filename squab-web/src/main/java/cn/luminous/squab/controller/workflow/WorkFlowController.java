package cn.luminous.squab.controller.workflow;


import cn.luminous.squab.entity.http.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/workflow")
@Controller
@Slf4j
public class WorkFlowController {


    /**
     * 跳转请假申请表单
     * @return
     */
    @RequestMapping("/qjAdd")
    public String toQjApply() {
        return "qj-add";
    }

    /**
     * 请假表单提交
     */
    @RequestMapping("/apply")
    @ResponseBody
    public R apply() {
        System.out.println("apply");
        return R.ok();
    }







}
