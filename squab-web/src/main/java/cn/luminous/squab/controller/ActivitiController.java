package cn.luminous.squab.controller;

import cn.luminous.squab.service.ActivitiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class ActivitiController {

    @Autowired
    private ActivitiService activitiService;

    @RequestMapping("activiti")
    public void test() {
        log.info(activitiService.toString());
    }


}
