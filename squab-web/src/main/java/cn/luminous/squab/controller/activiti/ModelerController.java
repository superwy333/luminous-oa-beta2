package cn.luminous.squab.controller.activiti;

import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RequestMapping("/modeler")
@Controller
@Slf4j
public class ModelerController {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping("/modelList")
    public String toModelList() {
        return "model-list";
    }

    @RequestMapping("/modelAdd")
    public String modelAdd() {
        return "model-add";
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




    @RequestMapping("/newModel")
    public Object newModel() throws UnsupportedEncodingException {
        //初始化一个空模型
        Model model = repositoryService.newModel();

        //设置一些默认信息
        String name = "new-process";
        String description = "";
        int revision = 1;
        String key = "process";

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
        return "redirect:/editor?modelId=" + id;
    }


}
