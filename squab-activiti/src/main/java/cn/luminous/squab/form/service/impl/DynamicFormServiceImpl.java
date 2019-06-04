package cn.luminous.squab.form.service.impl;

import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.form.service.DynamicFormService;
import cn.luminous.squab.mapper.DynamicFormMapper;
import cn.luminous.squab.model.DynamicFormModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dynamicFormService")
public class DynamicFormServiceImpl extends BaseServiceImpl<DynamicForm> implements DynamicFormService {

    @Autowired
    private DynamicFormMapper dynamicFormMapper;

    @Override
    protected IMapper<DynamicForm> getBaseMapper() {
        return this.dynamicFormMapper;
    }

    @Override
    public List<DynamicFormModel> queryDynamicFormPage(DynamicFormModel dynamicFormModel, Integer page, Integer limit) throws Exception {
        Map<String,Object> condition = new HashMap<>();
        if (page != null) {
            condition.put("page", (page - 1) * limit);
            condition.put("limit", limit);
        }
        return dynamicFormMapper.queryDynamicFormPage(condition);
    }
}
