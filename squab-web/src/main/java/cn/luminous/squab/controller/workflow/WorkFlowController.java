package cn.luminous.squab.controller.workflow;


import cn.hutool.json.JSONUtil;
import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.service.OaTaskService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/workflow")
@Controller
@Slf4j
public class WorkFlowController {

    @Autowired
    private OaTaskService oaTaskService;

    /**
     * 跳转我的待办
     * @return
     */
    @RequestMapping("/taskTodoList")
    public String toTaskToDo() {
        return "taskTodo-list";
    }


    /**
     * 跳转请假申请表单
     * @return
     */
    @RequestMapping("/qjAdd")
    public String toQjApply() {
        return "qj-add";
    }

    /**
     * 跳转请假审批表单
     * @return
     */
    @RequestMapping("/qjApprove")
    public String toQjApprove(Model model, String taskId) {
        try {
            OaTaskModel oaTaskModel = oaTaskService.queryTaskById(taskId);
            String data = oaTaskModel.getData();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            Map<String,Object> dataMap = gson.fromJson(data,Map.class);
            model.addAllAttributes(dataMap);
        }catch (Exception e) {
            // TODO 跳转到404页面
        }
        return "qj-approve";
    }

    /**
     * 表单提交
     */
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
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

    @RequestMapping(value = "/taskToDo", method = RequestMethod.POST)
    @ResponseBody
    public String taskToDo(@RequestBody Rq rq) {
        List<OaTaskModel> taskList = new ArrayList<>();
        try {
            log.debug("【查询代办开始】入参: " + rq.toString());
            taskList = oaTaskService.queryTaskToDo();
        }catch (Exception e) { // 统一再controller层捕获异常
            log.error("【查询待办任务失败】入参: " + rq.toString(), e);
            //return R.nok(e.getMessage());
        }

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        return gson.toJson(R.ok(taskList));



//        return JSONUtil.parse(R.ok(taskList));
    }







}
