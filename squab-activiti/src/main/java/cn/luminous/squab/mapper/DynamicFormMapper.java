package cn.luminous.squab.mapper;

import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.model.DynamicFormModel;
import cn.luminous.squab.mybatis.imapper.IMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DynamicFormMapper extends IMapper<DynamicForm> {

    List<DynamicFormModel> queryDynamicFormPage(@Param("condition") Map<String,Object> condition);


}