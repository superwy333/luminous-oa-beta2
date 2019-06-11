package cn.luminous.squab.controller.form;


import cn.hutool.core.bean.BeanUtil;
import cn.luminous.squab.controller.form.pojo.UeForm;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.form.service.DynamicFormService;
import cn.luminous.squab.model.DynamicFormModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dynamicForm")
@Slf4j
public class FormController {

    @Autowired
    private DynamicFormService dynamicFormService;


    @GetMapping("/formList")
    public ModelAndView toFormList(Model model) {
        ModelAndView m = new ModelAndView();
        m.setViewName("form-list");
        return m;
    }

    @PostMapping("/formList")
    @ResponseBody
    public String formList(@RequestBody Rq rq) {
        List<DynamicFormModel> dynamicFormModelList;
        Integer queryCount;
        try {
            Integer page = rq.getPage();
            Integer limit = rq.getLimit();
            DynamicFormModel queryCondition = new DynamicFormModel();
            dynamicFormModelList = dynamicFormService.queryDynamicFormPage(queryCondition, page, limit);
            queryCount = dynamicFormService.queryDynamicFormPage(queryCondition, null, null).size();
        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok(dynamicFormModelList, queryCount);
    }


    @RequestMapping("/edit")
    public String toFormEdit(@RequestParam(value = "formId", required = false) Long formId, Model model) {
        try {
            if (!BeanUtil.isEmpty(formId)) {
                DynamicForm dynamicForm = dynamicFormService.queryById(formId);
                model.addAttribute("id", dynamicForm.getId());
                model.addAttribute("formName", dynamicForm.getFormName());
                model.addAttribute("formCode", dynamicForm.getFormCode());
                model.addAttribute("html", dynamicForm.getFormHtml());
            }
        } catch (Exception e) {
            // TODO return 404
            log.error("【跳转表单编辑器失败】" + e);
        }
        return "form/form_edit2";
    }

    @PostMapping("/saveOrUpdateForm")
    @ResponseBody
    @CrossOrigin
    public String saveOrUpdateForm(@RequestBody Rq rq) {
        try {
            log.debug("【动态表单保存】入参: " + rq.toString());
            Map<String, Object> data = (Map<String, Object>) rq.getData();
            String idStr = (String) data.get("id");
            if (BeanUtil.isEmpty(idStr) || idStr.length() <= 0) { // 新增
                DynamicForm dynamicForm = new DynamicForm();
                dynamicForm.setFormName((String) data.get("formName"));
                dynamicForm.setFormCode((String) data.get("formCode"));
                dynamicForm.setFormHtml((String) data.get("template"));
                dynamicFormService.add(dynamicForm);
            } else { // 更新
                DynamicForm dynamicForm = dynamicFormService.queryById(Long.valueOf(idStr));
                dynamicForm.setFormName((String) data.get("formName"));
                dynamicForm.setFormCode((String) data.get("formCode"));
                dynamicForm.setFormHtml((String) data.get("template"));
                dynamicFormService.updateByIdSelective(dynamicForm);
            }
        } catch (Exception e) {
            log.error("【动态表单保存失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok();
    }


    @RequestMapping("/previewEditForm")
    public String previewEditForm(String formeditor, Integer form_id, Model model) {
        UeForm form = UeForm.praseTemplate(formeditor);
        model.addAttribute("html", form.getHtml());
        return "form/form_preview";
    }




}
