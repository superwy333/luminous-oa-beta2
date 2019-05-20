package cn.luminous.squab.controller.workflow;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.controller.form.pojo.UeForm;
import cn.luminous.squab.entity.BizMapping;
import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.OaTaskApprove;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.form.service.DynamicFormService;
import cn.luminous.squab.model.OaTaskApproveModel;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.model.OaTaskNodeModel;
import cn.luminous.squab.service.BizMappingService;
import cn.luminous.squab.service.OaTaskService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/workflow")
@Controller
@Slf4j
public class WorkFlowController{

    @Autowired
    private OaTaskService oaTaskService;
    @Autowired
    private DynamicFormService dynamicFormService;
    @Autowired
    private BizMappingService bizMappingService;

    /**
     * 跳转申请汇总页面
     * @return
     */
    @RequestMapping("/applyList")
    public String toApplyList() {
        // TODO 根据用户权限做可申请的业务筛选
        return "apply-list";

    }

    /**
     * 跳转我的待办
     * @return
     */
    @RequestMapping("/taskTodoList")
    public String toTaskToDo() {
        return "taskTodo-list";
    }

    @RequestMapping("/taskDoneList")
    public String toTaskDone() {
        return "taskDone-list";
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
     * 跳转请假申请表单
     * 使用自定义表单
     * @return
     */
    @RequestMapping("/qjAdd2")
    public String toQjApply2(Model model) {
        long id = 12;
        DynamicForm dynamicForm = dynamicFormService.queryById(id);
        model.addAttribute("html", dynamicForm.getFormHtml());
        model.addAttribute("name","哈哈老王");
        return "qj-add2";
    }


    /**
     * 任务申请
     * @param model
     * @return
     */
    @GetMapping("/apply")
    public ModelAndView apply(@RequestParam("bizKey") String bizKey, Model model) {
        ModelAndView m = new ModelAndView();
        try {
            BizMapping bizMapping = new BizMapping();
            bizMapping.setBizKey(bizKey);
            List<BizMapping> bizMappingList = bizMappingService.query(bizMapping);
            bizMapping = bizMappingList.get(0);
            DynamicForm dynamicForm = dynamicFormService.queryById(Long.valueOf(bizMapping.getFormId()));
            UeForm form = UeForm.praseTemplate(dynamicForm.getFormHtml());
            model.addAttribute("html",form.getHtml());
            model.addAttribute("bizKey",bizKey);
        }catch (Exception e) {
            e.printStackTrace();
        }
        m.setViewName("apply");
        return m;
    }






//    /**
//     * 跳转请假审批表单
//     * @return
//     */
//    @RequestMapping("/qjApprove")
//    public String toQjApprove(Model model, String taskId) {
//        try {
//            OaTaskModel oaTaskModel = oaTaskService.queryTaskById(taskId);
//            String data = oaTaskModel.getData();
//            Gson gson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                    .create();
//            Map<String,Object> dataMap = gson.fromJson(data,Map.class);
//            model.addAttribute("taskId",oaTaskModel.getTaskId());
//            model.addAttribute("id",oaTaskModel.getId());
//            model.addAllAttributes(dataMap);
//            // TODO 把当前登陆人加入模板参数
//            model.addAttribute("currentUser","008");
//        }catch (Exception e) {
//            // TODO 跳转到404页面
//        }
//        return "qj-approve";
//    }

//    @RequestMapping("/myTaskList")
//    public String myTaskList() {
//        return "myTask-list";
//    }

    @RequestMapping("/myTaskList")
    public ModelAndView myTaskList() {
        ModelAndView m = new ModelAndView();
        m.setViewName("myTask-list");
        return m;
    }

    @RequestMapping("/detail")
    public ModelAndView toDetail(@RequestParam("id") String id,
                                 Model model,
                                 @RequestParam("type") String type) { // type=1浏览type=2编辑
        ModelAndView m = new ModelAndView();
        try {
            OaTask oaTask = oaTaskService.queryById(Long.valueOf(id));
            BizMapping bizMapping = new BizMapping();
            bizMapping.setBizKey(oaTask.getBizKey());
            bizMapping = bizMappingService.query(bizMapping).get(0);
            // 表单
            DynamicForm dynamicForm = dynamicFormService.queryById(Long.valueOf(bizMapping.getFormId()));
            UeForm form = UeForm.praseTemplate(dynamicForm.getFormHtml());
            model.addAttribute("html",form.getHtml());

            // 流程流转的数据

            JSONArray jsonArray = JSONUtil.parseArray(oaTask.getData());
            model.addAttribute("data",jsonArray);
            //model.addAttribute("taskId",oaTaskModel.getTaskId());
            model.addAttribute("id",oaTask.getId());
            model.addAttribute("type",type);
        }catch (Exception e) {
            e.printStackTrace();
        }
        m.setViewName("task-detail");
        return m;
    }



    @RequestMapping("/approve")
    public ModelAndView toApprove(@RequestParam("id") String actTaskId, Model model) {
        ModelAndView m = new ModelAndView();
        try {
            OaTaskModel oaTaskModel = oaTaskService.queryTaskById(actTaskId);
            BizMapping bizMapping = new BizMapping();
            bizMapping.setBizKey(oaTaskModel.getBizKey());
            bizMapping = bizMappingService.query(bizMapping).get(0);

            // 表单
            DynamicForm dynamicForm = dynamicFormService.queryById(Long.valueOf(bizMapping.getFormId()));
            UeForm form = UeForm.praseTemplate(dynamicForm.getFormHtml());
            model.addAttribute("html",form.getHtml());

            // 流程流转的数据
            JSONArray jsonArray = JSONUtil.parseArray(oaTaskModel.getData());
            model.addAttribute("data",jsonArray);
            model.addAttribute("taskId",oaTaskModel.getTaskId());
            model.addAttribute("id",oaTaskModel.getId());

            // 审批需要的数据
            List<OaTaskNodeModel> oaTaskNodeModelList = oaTaskService.getCallBackNodes(oaTaskModel.getProcInstId());
            model.addAttribute("callBackNodes",oaTaskNodeModelList);
        }catch (Exception e) {
            e.printStackTrace();
        }
        m.setViewName("approve");
        return m;
    }


    /**
     * 表单提交
     */
    @PostMapping(value = "/apply")
    @ResponseBody
    public String apply(@RequestBody Rq rq) {
        OaTask oaTask = new OaTask();
        try {
            log.debug("【任务注册开始】入参: " + rq.toString());
            String bizKey = rq.getBizKey();
            BizMapping bizMapping = new BizMapping();
            bizMapping.setBizKey(bizKey);
            bizMapping = bizMappingService.query(bizMapping).get(0);
            oaTask.setBizKey(bizKey);
            oaTask.setData(JSONUtil.toJsonStr(rq.getData()));
            oaTask.setProcessKey(bizMapping.getProcessKey());
            oaTaskService.registerTask(oaTask);
        }catch (Exception e) { // 统一再controller层捕获异常
            log.error("【任务注册失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 审批
     */
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    @ResponseBody
    public String approve(@RequestBody Rq rq) {
        OaTask oaTask = new OaTask();
        try {
            log.debug("【审批开始】入参: " + rq.toString());
            Map<String,Object> data = (Map<String,Object>) rq.getData();
            OaTaskApprove oaTaskApprove = new OaTaskApprove();
            oaTaskApprove.setActTaskId((String) data.get("actTaskId"));
            oaTaskApprove.setApproveContent((String) data.get("approveContent"));
            oaTaskApprove.setApprover((String) data.get("currentUser"));
            oaTaskApprove.setApproveTime(new Date());
            oaTaskApprove.setOaTaskId(((Integer)data.get("oaTaskId")).longValue());
            if (Constant.BIZ_KEY.PASS.equals(rq.getBizKey())) { // 流程通过
                oaTaskApprove.setApproveResult(Constant.TASK_APPROVE_RESULT.PASS);
                oaTaskService.approveTask(oaTaskApprove);
            }else if (Constant.BIZ_KEY.REJECT.equals(rq.getBizKey())) { // 流程驳回
                String hisTaskId = (String) data.get("callBackNode"); // 流程回退的历史任务id
                if ("start".equals(hisTaskId)) { // 退回发起人
                    oaTaskApprove.setApproveResult(Constant.TASK_APPROVE_RESULT.REJECT);
                    oaTaskService.rejectTask(oaTaskApprove);
                }else { // 退回到历史节点
                    oaTaskService.callBackTaskToHisTask(hisTaskId);
                }
            }
        }catch (Exception e) { // 统一再controller层捕获异常
            log.error("【审批失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok();
    }


    /**
     * 我的代办列表
     * @param rq
     * @return
     */
    @RequestMapping(value = "/taskToDo", method = RequestMethod.POST)
    @ResponseBody
    public String taskToDo(@RequestBody Rq rq) {
        List<OaTaskModel> taskList;
        try {
            log.debug("【查询代办开始】入参: " + rq.toString());
            taskList = oaTaskService.queryTaskToDo();
        }catch (Exception e) { // 统一再controller层捕获异常
            log.error("【查询待办任务失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(taskList);

    }

    /**
     * 我的已办列表
     * @param rq
     * @return
     */
    @RequestMapping(value = "/taskDone", method = RequestMethod.POST)
    @ResponseBody
    public String taskDone(@RequestBody Rq rq) {
        List<OaTaskModel> taskList;
        try {
            log.debug("【查询已办开始】入参: " + rq.toString());
            taskList = oaTaskService.queryTaskDone();
        }catch (Exception e) { // 统一再controller层捕获异常
            log.error("【查询已办任务失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(taskList);

    }

    /**
     * 审批流转记录
     * @param rq
     * @return
     */
    @RequestMapping(value = "/approveDetails", method = RequestMethod.POST)
    @ResponseBody
    public String approveDetails(@RequestBody Rq rq) {
        List<OaTaskApproveModel> oaTaskApproveModelList;
        try {
            log.debug("【查询审批记录开始】入参: " + rq.toString());
            Map<String,Object> data = (Map<String,Object>) rq.getData();
            oaTaskApproveModelList = oaTaskService.queryTaskApproveDetails(Long.valueOf((Integer) data.get("oaTaskId")));
        }catch (Exception e) {
            log.error("【查询审批记录失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(oaTaskApproveModelList);
    }


    /**
     * 我的申请
     * @param rq
     * @return
     */
    @RequestMapping(value = "/myTaskList", method = RequestMethod.POST)
    @ResponseBody
    public String myTaskList(@RequestBody Rq rq) {
        List<OaTaskModel> oaTaskModelList;
        try {
            // TODO 获取当前登陆人
            String currentUserCode = "008";
            oaTaskModelList = oaTaskService.queryMyTask(currentUserCode);
            log.debug("【查询我的任务开始】入参: " + rq.toString());
        }catch (Exception e) {
            log.error("【查询我的任务失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(oaTaskModelList);
    }



}
