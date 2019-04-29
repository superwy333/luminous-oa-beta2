package cn.luminous.squab.mybatis.mapper.base.update;

import cn.luminous.squab.mybatis.mapper.provider.LuminousBaseUpdateProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 通用Mapper接口,更新(处理乐观锁)
 *
 * @param <T> 不能为空
 * @author create by liwuyang on 2016-07-17 16:07 
 */
public interface LuminousUpdateByPrimaryKeySelectiveMapper<T> {

    /**
     * 根据主键更新属性不为null的值(处理乐观锁)
     *
     * @param record
     * @return
     */
    @UpdateProvider(type = LuminousBaseUpdateProvider.class, method = "dynamicSQL")
    int updateCASByPrimaryKeySelective(T record);

}