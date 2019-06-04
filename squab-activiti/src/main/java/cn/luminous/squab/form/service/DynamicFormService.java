package cn.luminous.squab.form.service;

import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.model.DynamicFormModel;
import cn.luminous.squab.service.BaseService;

import java.util.List;

public interface DynamicFormService extends BaseService<DynamicForm> {



    List<DynamicFormModel> queryDynamicFormPage(DynamicFormModel dynamicFormModel, Integer page, Integer limit) throws Exception;





}
