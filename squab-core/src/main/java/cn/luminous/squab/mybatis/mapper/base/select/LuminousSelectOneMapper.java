package cn.luminous.squab.mybatis.mapper.base.select;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.provider.base.BaseSelectProvider;

/**
 *  通用Mapper接口,查询
 *
 * @param <T> 不能为空
 * Created by liwuyang on 2017/3/17.
 */
public interface LuminousSelectOneMapper<T> {
    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    T selectOne(T record);
}