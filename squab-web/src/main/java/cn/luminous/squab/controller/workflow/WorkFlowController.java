package cn.luminous.squab.controller.workflow;


import cn.hutool.json.JSONUtil;
import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.service.OaTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/workflow")
@Controller
@Slf4j
public class WorkFlowController {

    @Autowired
    private OaTaskService oaTaskService;


    /**
     * 跳转请假申请表单
     * @return
     */
    @RequestMapping("/qjAdd")
    public String toQjApply() {
        return "qj-add";
    }

    /**
     * 表单提交
     */
    @RequestMapping("/apply")
    @ResponseBody
    public R apply(@RequestBody Rq rq) {
        OaTask oaTask = new OaTask();
        try {
            log.debug("【任务注册开始】入参: " + rq.toString());
            oaTask.setBizKey(rq.getBizKey());
            oaTask.setData(JSONUtil.toJsonStr(rq.getData()));
            oaTaskService.registerTask(oaTask);
        }catch (Exception e) { // 统一再controller层捕获异常
            log.error("【任务注册失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok();
    }







}
