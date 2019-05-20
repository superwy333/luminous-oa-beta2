package cn.luminous.squab.controller.activiti;

import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.form.service.DynamicFormService;
import cn.luminous.squab.service.ActivitiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RequestMapping("/modeler")
@Controller
@Slf4j
public class ModelerController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DynamicFormService dynamicFormService;
    @Autowired
    private ActivitiService activitiService;

    @RequestMapping("/modelList")
    public String toModelList() {
        return "model-list";
    }

    @RequestMapping("/modelAdd")
    public String modelAdd() {
        return "model-add";
    }

    @RequestMapping("/deploy")
    public String toDeploy(org.springframework.ui.Model model,
                           @RequestParam(value = "modelId") String modelId) {

        // 查询表单
        List<DynamicForm> dynamicFormList = dynamicFormService.query(new DynamicForm());
        model.addAttribute("formList",dynamicFormList);
        model.addAttribute("modelId",modelId);

        return "model-deploy";

    }

    @PostMapping("/modelList")
    @ResponseBody
    public String modelList(@RequestBody Rq rq) {
        List<Model> modelList;
        try {
            log.debug("【查询模型开始】入参: " + rq.toString());
            // TODO 分页的处理
            Integer page = 1;
            Integer limit = 10;
            modelList = repositoryService.createModelQuery().listPage(limit * (page - 1), limit);
            long count = repositoryService.createModelQuery().count();
        }catch (Exception e) {
            log.error("【查询模型失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(modelList);
    }

    @PostMapping("/newModel")
    @ResponseBody
    public String newModel(@RequestBody Rq rq) throws UnsupportedEncodingException {
        Map<String,String> data = (Map<String,String>) rq.getData();
        //初始化一个空模型
        Model model = repositoryService.newModel();

        //设置一些默认信息
        String name = data.get("name");
        String description = data.get("description");
        int revision = 1;
        String key = data.get("key");

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id,editorNode.toString().getBytes("utf-8"));
        String url = "/editor?modelId="+id;
        return R.ok(url);
    }

    @PostMapping("/deleteModel")
    @ResponseBody
    public String deleteModel(@RequestBody Rq rq) {
        try {
            Map<String,String> data = (Map<String,String>) rq.getData();
            repositoryService.deleteModel(data.get("id"));
        }catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();
    }

    @PostMapping("/deploy")
    @ResponseBody
    public String deploy(@RequestBody Rq rq) {
        try {
            Map<String,String> data = (Map<String,String>) rq.getData();
            String modelId = data.get("modelId");
            String bizKey = data.get("bizKey");
            String formId = data.get("formId");
            activitiService.modeDeploy(bizKey, modelId, Long.valueOf(formId));
        }catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();
    }
}
