package cn.luminous.squab.form.service.impl;

import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.form.service.DynamicFormService;
import cn.luminous.squab.mapper.DynamicFormMapper;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dynamicFormService")
public class DynamicFormServiceImpl extends BaseServiceImpl<DynamicForm> implements DynamicFormService {

    @Autowired
    private DynamicFormMapper dynamicFormMapper;

    @Override
    protected IMapper<DynamicForm> getBaseMapper() {
        return this.dynamicFormMapper;
    }
}
