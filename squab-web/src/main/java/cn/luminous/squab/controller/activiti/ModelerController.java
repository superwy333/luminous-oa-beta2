package cn.luminous.squab.controller.activiti;

import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.form.service.DynamicFormService;
import cn.luminous.squab.service.ActivitiService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
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

    @GetMapping("/modelList")
    public String toModelList() {
        return "model-list";
    }

    @GetMapping("/modelAdd")
    public String modelAdd() {
        return "model-add";
    }

    @GetMapping("/modelCopy")
    public String modelCopy(org.springframework.ui.Model model,
                            @RequestParam(value = "modelId") String modelId
    ) {
        model.addAttribute("modelId", modelId);
        return "model-copy";
    }

    @PostMapping("/modelCopy")
    @ResponseBody
    public String modelCopy(@RequestBody Rq rq) {
        try {
            Map<String, String> data = (Map<String, String>) rq.getData();
            String modelId = data.get("modelId");
            String name = data.get("name");
            Model sourceModel = repositoryService.createModelQuery().modelId(modelId).singleResult();
            ObjectNode sourceObjectNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelId));
            Model newModel = repositoryService.newModel();

            String metaInfo =  sourceModel.getMetaInfo();
            JSONObject metaInfoJson = JSONObject.parseObject(metaInfo);
            metaInfoJson.put("name",name);
            newModel.setMetaInfo(JSONObject.toJSONString(metaInfoJson));
            newModel.setName(name);
            newModel.setCategory(sourceModel.getCategory());
            newModel.setVersion(1);
            repositoryService.saveModel(newModel);
            ObjectNode editorNode = sourceObjectNode.deepCopy();
            ObjectNode properties = new ObjectMapper().createObjectNode();
            properties.put("process_id", newModel.getKey());
            properties.put("process_author", "tt");
            properties.put("name", newModel.getName());
            editorNode.set("properties", properties);
            repositoryService.addModelEditorSource(newModel.getId(), editorNode.toString().getBytes("utf-8"));
        } catch (Exception e) {
            log.error("模型复制失败:", e);
            R.nok(e.getMessage());
        }
        return R.ok();
    }


    @GetMapping("/deploy")
    public String toDeploy(org.springframework.ui.Model model,
                           @RequestParam(value = "modelId") String modelId) {

        // 查询表单
        List<DynamicForm> dynamicFormList = dynamicFormService.query(new DynamicForm());
        model.addAttribute("formList", dynamicFormList);
        model.addAttribute("modelId", modelId);

        return "model-deploy";

    }

    @PostMapping("/modelList")
    @ResponseBody
    public String modelList(@RequestBody Rq rq) {
        List<Model> modelList;
        Long count;
        try {
            log.debug("【查询模型开始】入参: " + rq.toString());
            Integer page = rq.getPage();
            Integer limit = rq.getLimit();
            modelList = repositoryService.createModelQuery().listPage(limit * (page - 1), limit);
            count = repositoryService.createModelQuery().count();
        } catch (Exception e) {
            log.error("【查询模型失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(modelList, count.intValue());
    }

    @PostMapping("/newModel")
    @ResponseBody
    public String newModel(@RequestBody Rq rq) throws UnsupportedEncodingException {
        Map<String, String> data = (Map<String, String>) rq.getData();
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
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        String url = "/editor?modelId=" + id;
        return R.ok(url);
    }

    @PostMapping("/deleteModel")
    @ResponseBody
    public String deleteModel(@RequestBody Rq rq) {
        try {
            Map<String, String> data = (Map<String, String>) rq.getData();
            repositoryService.deleteModel(data.get("id"));
        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();
    }

    @PostMapping("/deploy")
    @ResponseBody
    public String deploy(@RequestBody Rq rq) {
        try {
            Map<String, String> data = (Map<String, String>) rq.getData();
            String modelId = data.get("modelId");
            String bizKey = data.get("bizKey");
            String formId = data.get("formId");
            String bizName = data.get("bizName");
            activitiService.modeDeploy(bizKey, modelId, Long.valueOf(formId), bizName);
        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();
    }

    @PostMapping("/updateDeploy")
    @ResponseBody
    public String update(@RequestBody Rq rq) {
        try {
            Map<String, String> data = (Map<String, String>) rq.getData();
            String modelId = data.get("modelId");
            activitiService.updateDeploy(modelId);
        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();

    }
}
