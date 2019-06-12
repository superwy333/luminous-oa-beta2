package cn.luminous.squab.controller.workflow;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.controller.form.pojo.UeForm;
import cn.luminous.squab.entity.*;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.form.service.DynamicFormService;
import cn.luminous.squab.model.OaTaskApproveModel;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.model.OaTaskNodeModel;
import cn.luminous.squab.model.SysUserModel;
import cn.luminous.squab.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/workflow")
@Controller
@Slf4j
public class WorkFlowController {

    @Autowired
    private OaTaskService oaTaskService;
    @Autowired
    private DynamicFormService dynamicFormService;
    @Autowired
    private BizMappingService bizMappingService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private OaTaskAttachmentService oaTaskAttachmentService;
    @Autowired
    private SysConfDictitemService sysConfDictitemService;

    /**
     * 跳转申请汇总页面
     *
     * @return
     */
    @RequestMapping("/applyList")
    public String toApplyList() {
        // TODO 根据用户权限做可申请的业务筛选
        return "apply-list";

    }

    /**
     * 跳转我的待办
     *
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
     *
     * @return
     */
    @RequestMapping("/qjAdd")
    public String toQjApply() {
        return "qj-add";
    }

    /**
     * 跳转请假申请表单
     * 使用自定义表单
     *
     * @return
     */
    @RequestMapping("/qjAdd2")
    public String toQjApply2(Model model) {
        long id = 12;
        DynamicForm dynamicForm = dynamicFormService.queryById(id);
        model.addAttribute("html", dynamicForm.getFormHtml());
        model.addAttribute("name", "哈哈老王");
        return "qj-add2";
    }


    /**
     * 任务申请
     *
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
            model.addAttribute("html", form.getHtml());
            model.addAttribute("bizKey", bizKey);
            // 获取流水号
            //String taskNo = oaTaskService.getTaskNo();
            //model.addAttribute("taskNo",taskNo);
            // 获取申请人信息
            SysUer currentUser = (SysUer) SecurityUtils.getSubject().getPrincipal();
            SysUserModel sysUserModel = sysUserService.queryUserInfo(currentUser.getUserCode());
            model.addAttribute("sysUser", sysUserModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setViewName("apply");
        return m;
    }

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
            model.addAttribute("html", form.getHtml());

            // 流程流转的数据

            JSONArray jsonArray = JSONUtil.parseArray(oaTask.getData());
            model.addAttribute("data", jsonArray);
            //model.addAttribute("taskId",oaTaskModel.getTaskId());
            model.addAttribute("id", oaTask.getId());
            model.addAttribute("type", type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setViewName("task-detail");
        return m;
    }


    @GetMapping("/approve")
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
            model.addAttribute("html", form.getHtml());

            // 流程流转的数据
            JSONArray jsonArray = JSONUtil.parseArray(oaTaskModel.getData());
            model.addAttribute("data", jsonArray);
            model.addAttribute("taskId", oaTaskModel.getTaskId());
            model.addAttribute("id", oaTaskModel.getId());
            model.addAttribute("lsh", oaTaskModel.getTaskNo());

            // 审批需要的数据
            List<OaTaskNodeModel> oaTaskNodeModelList = oaTaskService.getCallBackNodes(oaTaskModel.getProcInstId());
            model.addAttribute("callBackNodes", oaTaskNodeModelList);

            // 附件
            OaTaskAttachment oaTaskAttachment = new OaTaskAttachment();
            oaTaskAttachment.setOaTaskId(oaTaskModel.getId());
            List<OaTaskAttachment> oaTaskAttachmentList = oaTaskAttachmentService.query(oaTaskAttachment);
            model.addAttribute("oaTaskAttachmentList", oaTaskAttachmentList);
        } catch (Exception e) {
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
        OaTask oaTaskAfterRegister;
        try {
            log.debug("【任务注册开始】入参: " + rq.toString());
            String bizKey = rq.getBizKey();
            BizMapping bizMapping = new BizMapping();
            bizMapping.setBizKey(bizKey);
            bizMapping = bizMappingService.query(bizMapping).get(0);
            oaTask.setBizKey(bizKey);
            oaTask.setData(JSONUtil.toJsonStr(rq.getData()));
            oaTask.setProcessKey(bizMapping.getProcessKey());
            SysUer currentUser = (SysUer) SecurityUtils.getSubject().getPrincipal();
            oaTask.setApplyCode(currentUser.getUserCode());
            // 获取流水号
            oaTask.setTaskNo(oaTaskService.getTaskNo());
            // 填报时间
            oaTask.setApplyTime(new Date());
            oaTaskAfterRegister = oaTaskService.registerTask(oaTask);
        } catch (Exception e) { // 统一再controller层捕获异常
            log.error("【任务注册失败】入参: " + rq.toString(), e);
            //return R.nok(e.getMessage());
            return R.nok("没有发起流程的权限，请联系管理员！" + e.getMessage());
        }
        return R.ok(oaTaskAfterRegister);
    }

    /**
     * 审批
     */
    @PostMapping(value = "/approve")
    @ResponseBody
    public String approve(@RequestBody Rq rq) {
        OaTask oaTask = new OaTask();
        try {
            log.debug("【审批开始】入参: " + rq.toString());
            Map<String, Object> data = (Map<String, Object>) rq.getData();
            OaTaskApprove oaTaskApprove = new OaTaskApprove();
            oaTaskApprove.setActTaskId((String) data.get("actTaskId"));
            oaTaskApprove.setApproveContent((String) data.get("approveContent"));
            oaTaskApprove.setApprover((String) data.get("currentUser"));
            oaTaskApprove.setApproveTime(new Date());
            oaTaskApprove.setOaTaskId(((Integer) data.get("oaTaskId")).longValue());
            SysUer currentUser = (SysUer) SecurityUtils.getSubject().getPrincipal();
            oaTaskApprove.setApprover(currentUser.getUserCode());

            if (Constant.BIZ_KEY.PASS.equals(rq.getBizKey())) { // 流程通过
                oaTaskApprove.setApproveResult(Constant.TASK_APPROVE_RESULT.PASS);
                oaTaskService.approveTask(oaTaskApprove);
            } else if (Constant.BIZ_KEY.REJECT.equals(rq.getBizKey())) { // 流程驳回
                String hisTaskId = (String) data.get("callBackNode"); // 流程回退的历史任务id
                if ("start".equals(hisTaskId)) { // 退回发起人
                    oaTaskApprove.setApproveResult(Constant.TASK_APPROVE_RESULT.REJECT);
                    oaTaskService.rejectTask(oaTaskApprove);
                } else { // 退回到历史节点
                    // TODO 这边不写审批记录 是个BUG
                    oaTaskService.callBackTaskToHisTask(hisTaskId);
                }
            }
        } catch (Exception e) { // 统一再controller层捕获异常
            log.error("【审批失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok();
    }


    /**
     * 我的代办列表
     *
     * @param rq
     * @return
     */
    @RequestMapping(value = "/taskToDo", method = RequestMethod.POST)
    @ResponseBody
    public String taskToDo(@RequestBody Rq rq) {
        List<OaTaskModel> taskList;
        Integer queryCount;
        try {
            log.debug("【查询代办开始】入参: " + rq.toString());
            SysUer currentUser = (SysUer) SecurityUtils.getSubject().getPrincipal();
            taskList = oaTaskService.queryTaskToDoPage(currentUser.getUserCode(), rq.getPage(), rq.getLimit());
            queryCount = oaTaskService.queryTaskToDoPage(currentUser.getUserCode(), null, null).size();
            // 处理一下业务类型
            taskList.stream().forEach(oaTaskModel -> {
                BizMapping bizMapping = new BizMapping();
                bizMapping.setBizKey(oaTaskModel.getBizKey());
                bizMapping = bizMappingService.queryOne(bizMapping);
                oaTaskModel.setBizName(bizMapping.getBizName());
            });
        } catch (Exception e) { // 统一再controller层捕获异常
            log.error("【查询待办任务失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(taskList, queryCount);

    }

    /**
     * 我的已办列表
     *
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
        } catch (Exception e) { // 统一再controller层捕获异常
            log.error("【查询已办任务失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(taskList, taskList.size());

    }

    /**
     * 审批流转记录
     *
     * @param rq
     * @return
     */
    @RequestMapping(value = "/approveDetails", method = RequestMethod.POST)
    @ResponseBody
    public String approveDetails(@RequestBody Rq rq) {
        List<OaTaskApproveModel> oaTaskApproveModelList;
        try {
            log.debug("【查询审批记录开始】入参: " + rq.toString());
            Map<String, Object> data = (Map<String, Object>) rq.getData();
            oaTaskApproveModelList = oaTaskService.queryTaskApproveDetails(Long.valueOf((Integer) data.get("oaTaskId")));
            // 处理审批人名字
            oaTaskApproveModelList.stream().forEach(oaTaskApproveModel -> {
                SysUer sysUer = new SysUer();
                sysUer.setUserCode(oaTaskApproveModel.getApprover());
                sysUer = sysUserService.queryOne(sysUer);
                oaTaskApproveModel.setApprover(sysUer.getName());

            });
        } catch (Exception e) {
            log.error("【查询审批记录失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(oaTaskApproveModelList);
    }


    /**
     * 我的申请
     *
     * @param rq
     * @return
     */
    @RequestMapping(value = "/myTaskList", method = RequestMethod.POST)
    @ResponseBody
    public String myTaskList(@RequestBody Rq rq) {
        List<OaTaskModel> oaTaskModelList;
        Integer queryCount;
        try {
            // 获取当前登陆人
            SysUer currentUser = (SysUer) SecurityUtils.getSubject().getPrincipal();
            oaTaskModelList = oaTaskService.queryMyTaskPage(currentUser.getUserCode(), rq.getPage(), rq.getLimit());
            queryCount = oaTaskService.queryMyTaskPage(currentUser.getUserCode(), null, null).size();
            // 处理一下当前指派人和业务类型
            oaTaskModelList.stream().forEach(oaTaskModel -> {

                if (oaTaskModel.getAssignee() != null) {
                    SysUer sysUer = new SysUer();
                    sysUer.setUserCode(oaTaskModel.getAssignee());
                    sysUer = sysUserService.queryOne(sysUer);
                    if (sysUer != null) {
                        oaTaskModel.setAssignee(sysUer.getName());
                    }
                }
                BizMapping bizMapping = new BizMapping();
                bizMapping.setBizKey(oaTaskModel.getBizKey());
                bizMapping = bizMappingService.queryOne(bizMapping);
                oaTaskModel.setBizName(bizMapping.getBizName());
            });
            log.debug("【查询我的任务开始】入参: " + rq.toString());
        } catch (Exception e) {
            log.error("【查询我的任务失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(oaTaskModelList, queryCount);
    }


    /**
     * 通过流的方式上传文件
     * 测试文件上传功能
     *
     * @RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile 对象
     */
    @RequestMapping("/oaTaskAttachment")
    @ResponseBody
    @CrossOrigin
    public String fileUpload(@RequestParam("file") MultipartFile file,
                             @RequestParam("oaTaskId") Long oaTaskId) throws IOException {


        //用来检测程序运行时间
        long startTime = System.currentTimeMillis();
        System.out.println("fileName：" + file.getOriginalFilename());
        try {
            // 查询oaTask
            OaTask oaTask = oaTaskService.queryById(oaTaskId);
            // 查询文件上传路径配置
            SysConfDictitem sysConfDictitem = sysConfDictitemService.queryByCode("attachment_uploader_config", "upload_url");
            String baseUrl = sysConfDictitem.getDicItemValue();
            //baseUrl = baseUrl.replaceAll("/", "");
            // 文件名字
            String fileName = file.getOriginalFilename();
            //attachment
            String realtiviUrl = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + oaTask.getTaskNo();
            String directionName = baseUrl + "/" + realtiviUrl;
            File folder = new File(directionName);
            if (!(folder.isDirectory())) {
                folder.mkdirs();
            }
            String fullUrl = directionName + "/" + fileName;
            //获取输出流
            OutputStream os = new FileOutputStream(fullUrl);
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is = file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while ((temp = is.read()) != (-1)) {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();

            // 保存数据库
            OaTaskAttachment oaTaskAttachment = new OaTaskAttachment();
            oaTaskAttachment.setFileName(fileName);
            oaTaskAttachment.setOaTaskId(oaTaskId);
            oaTaskAttachment.setUrl(realtiviUrl);
            oaTaskAttachmentService.add(oaTaskAttachment);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return R.nok();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("方法一的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        return R.ok();
    }


}
