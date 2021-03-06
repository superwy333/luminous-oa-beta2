package cn.luminous.squab.controller.workflow;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.controller.BaseController;
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
import cn.luminous.squab.util.BeanUtil;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/workflow")
@Controller
@Slf4j
public class WorkFlowController extends BaseController {

    @Autowired
    private OaTaskService oaTaskService;
    @Autowired
    private OaTaskApproveService oaTaskApproveService;
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

    @GetMapping("/taskDoneList")
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
            String applyTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            model.addAttribute("sqrq", applyTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setViewName("apply");
        return m;
    }

    @GetMapping("/myTaskList")
    public ModelAndView myTaskList(Model model) {
        ModelAndView m = new ModelAndView();
        try {
            // 业务类型下拉菜单
            List<BizMapping> bizMappingList = bizMappingService.query(new BizMapping());
            model.addAttribute("bizMappingList", bizMappingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setViewName("myTask-list");
        return m;
    }

    @RequestMapping("/detail")
    public ModelAndView toDetail(@RequestParam("id") String id,
                                 Model model,
                                 @RequestParam("type") String type) { // type=1浏览type=2编辑type=3打印
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
            model.addAttribute("bizKey", oaTask.getBizKey());
            model.addAttribute("type", type);
            model.addAttribute("lsh", oaTask.getTaskNo());

            // 附件
            OaTaskAttachment oaTaskAttachment = new OaTaskAttachment();
            oaTaskAttachment.setOaTaskId(Long.valueOf(id));
            List<OaTaskAttachment> oaTaskAttachmentList = oaTaskAttachmentService.query(oaTaskAttachment);
            model.addAttribute("oaTaskAttachmentList", oaTaskAttachmentList);

            // 打印需要的流程流转记录
            if (Constant.TASK_DETAIL_TYPE.PRINT.equals(type)) {
                parsePrintData(model, Long.valueOf(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Constant.TASK_DETAIL_TYPE.DETAIL.equals(type)) {
            m.setViewName("task-detail");
        } else if (Constant.TASK_DETAIL_TYPE.EDIT.equals(type)) {
            m.setViewName("task-edit");
        } else if (Constant.TASK_DETAIL_TYPE.PRINT.equals(type)) {
            m.setViewName("task-detail2");
        }
        return m;
    }

    private void removeSameAndGetLast(List<OaTaskApproveModel> oaTaskApproveModelList) {
        for (int i = 0; i < oaTaskApproveModelList.size() - 1; i++) {
            for (int j = oaTaskApproveModelList.size() - 1; j > i; j--) {
                if (oaTaskApproveModelList.get(j).getNodeName().equals(oaTaskApproveModelList.get(i).getNodeName())) {
                    oaTaskApproveModelList.remove(i);
                }
            }
        }
    }

    private void parsePrintData(Model model, Long oaTaskId) throws Exception {
        List<OaTaskApproveModel> oaTaskApproveModelList = oaTaskApproveService.queryOaTaskApproveForPrint(oaTaskId);
        removeSameAndGetLast(oaTaskApproveModelList);
        // 处理approveResult
        oaTaskApproveModelList.stream().forEach(oaTaskApproveModel -> {
            oaTaskApproveModel.setApproveResult("同意");
            oaTaskApproveModel.setMh(":");
            oaTaskApproveModel.setApproveTimeStr(BeanUtil.formatDate(oaTaskApproveModel.getApproveTime(), "yyyy-MM-dd HH:mm:ss"));
        });
        // 拆分两行
        if (!BeanUtil.isEmpty(oaTaskApproveModelList) && oaTaskApproveModelList.size() > 3) {
            List<OaTaskApproveModel> oaTaskApproveModelList1 = new ArrayList<>();
            List<OaTaskApproveModel> oaTaskApproveModelList2 = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                oaTaskApproveModelList1.add(oaTaskApproveModelList.get(i));
            }
            for (int i = 3; i < oaTaskApproveModelList.size(); i++) {
                oaTaskApproveModelList2.add(oaTaskApproveModelList.get(i));
            }
            if (oaTaskApproveModelList.size() < 6) { // 补齐
                int offset = 6 - oaTaskApproveModelList.size();
                for (int i = 0; i < offset; i++) {
                    OaTaskApproveModel oaTaskApproveModel = new OaTaskApproveModel();
                    oaTaskApproveModel.setNodeName("");
                    oaTaskApproveModel.setApprover("");
                    oaTaskApproveModel.setApproveTimeStr("");
                    oaTaskApproveModel.setApproveResult("");
                    oaTaskApproveModel.setMh("");
                    oaTaskApproveModelList2.add(oaTaskApproveModel);
                }
            }
            model.addAttribute("oaTaskApproveModelList1", oaTaskApproveModelList1);
            model.addAttribute("oaTaskApproveModelList2", oaTaskApproveModelList2);
        } else {
            model.addAttribute("oaTaskApproveModelList1", oaTaskApproveModelList);
        }
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
            return R.nok("没有发起流程的权限，请联系管理员！" + e.getMessage());
        }
        return R.ok(oaTaskAfterRegister);
    }

    /**
     * 编辑
     *
     * @param rq
     * @return
     */
    @PostMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody Rq rq) {
        OaTask oaTask;
        try {
            oaTask = oaTaskService.queryById(rq.getOaTaskId());
            oaTask.setData(JSONUtil.toJsonStr(rq.getData()));
            oaTaskService.updateByIdSelective(oaTask);
        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok(oaTask);
    }

    /**
     * 发起任务
     *
     * @param rq
     * @return
     */
    @PostMapping(value = "/startProcess")
    @ResponseBody
    public String startProcess(@RequestBody Rq rq) {
        try {
            OaTask oaTask = oaTaskService.queryById(rq.getOaTaskId());
            oaTaskService.startTask(oaTask);
        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();
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
    @PostMapping(value = "/taskToDo")
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
                if (bizMapping != null) {
                    oaTaskModel.setBizName(bizMapping.getBizName());
                }
            });
        } catch (Exception e) { // 统一再controller层捕获异常
            log.error("【查询待办任务失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(taskList, queryCount);

    }

    /**
     * 代办数量
     * TODO
     *
     * @param rq
     * @return
     */
    @PostMapping(value = "/taskTodoCount")
    @ResponseBody
    public String taskTodoCount(@RequestBody Rq rq) {
        try {

        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();
    }


    /**
     * 我的已办列表
     *
     * @param rq
     * @return
     */
    @PostMapping(value = "/taskDone")
    @ResponseBody
    public String taskDone(@RequestBody Rq rq) {
        List<OaTaskModel> taskList;
        Integer queryCount;
        try {
            log.debug("【查询已办开始】入参: " + rq.toString());
            SysUer currentUser = (SysUer) SecurityUtils.getSubject().getPrincipal();
            taskList = oaTaskService.queryTaskDonePage(currentUser.getUserCode(), rq.getPage(), rq.getLimit());
            queryCount = oaTaskService.queryTaskDonePage(currentUser.getUserCode(), null, null).size();
            // 处理一下业务类型和当前指派人
            parseTaskList(taskList);
        } catch (Exception e) { // 统一再controller层捕获异常
            log.error("【查询已办任务失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(taskList, queryCount);

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
    @PostMapping(value = "/myTaskList")
    @ResponseBody
    public String myTaskList(@RequestBody Rq rq) {
        List<OaTaskModel> oaTaskModelList;
        Integer queryCount;
        try {
            // 获取当前登陆人
            SysUer currentUser = (SysUer) SecurityUtils.getSubject().getPrincipal();

            // 查询条件
            Map<String, Object> condition = parseListQueryCondition(rq);
            // 如果当前登陆人是管理员，则不需要加入用户搜索纬度
            if (!currentUser.getUserCode().equals("tt")) {
                condition.put("userCode", currentUser.getUserCode());
            }
            oaTaskModelList = oaTaskService.queryMyTaskPage(condition);
            condition.put("page", null);
            condition.put("limit", null);
            queryCount = oaTaskService.queryMyTaskPage(condition).size();
            // 处理一下当前指派人和业务类型
            parseTaskList(oaTaskModelList);
            log.debug("【查询我的任务开始】入参: " + rq.toString());
        } catch (Exception e) {
            log.error("【查询我的任务失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(oaTaskModelList, queryCount);
    }

    /**
     * 处理用于返回给前端的任务列表
     *
     * @param oaTaskModelList
     */
    private void parseTaskList(List<OaTaskModel> oaTaskModelList) {
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
            if (bizMapping != null) {
                oaTaskModel.setBizName(bizMapping.getBizName());
            }

        });
    }


    /**
     * 申请人本人撤回申请
     * 只有在途申请才可以撤回
     *
     * @param rq
     * @return
     */
    @PostMapping("/cancelProcess")
    @ResponseBody
    public String cancelProcess(@RequestBody Rq rq) {
        try {
            Map<String, Object> data = (Map<String, Object>) rq.getData();
            Long oaTaskId = Long.valueOf((Integer) data.get("id"));
            OaTaskApprove oaTaskApprove = new OaTaskApprove();
            oaTaskApprove.setOaTaskId(oaTaskId);
            SysUer sysUer = (SysUer) SecurityUtils.getSubject().getPrincipal();
            oaTaskApprove.setApprover(sysUer.getUserCode());
            oaTaskApprove.setApproveResult(Constant.TASK_APPROVE_RESULT.CANCEL);
            oaTaskService.canCelTask(oaTaskApprove);
        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();
    }


    /**
     * 删除附件
     *
     * @return
     */
    @PostMapping("/deleteAttachment")
    @ResponseBody
    public String deleteAttachment(@RequestBody Rq rq) {
        try {
            Map<String, Object> data = (Map<String, Object>) rq.getData();
            Long attachmentId = Long.valueOf((String) data.get("id"));
            OaTaskAttachment oaTaskAttachment = oaTaskAttachmentService.queryById(attachmentId);
            oaTaskAttachmentService.remove(oaTaskAttachment);
        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();
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
