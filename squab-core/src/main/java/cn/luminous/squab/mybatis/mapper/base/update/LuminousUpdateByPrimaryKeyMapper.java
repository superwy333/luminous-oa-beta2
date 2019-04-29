package cn.luminous.squab.mybatis.mapper.base.update;

import cn.luminous.squab.mybatis.mapper.provider.LuminousBaseUpdateProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 通用Mapper接口,更新(处理乐观锁)
 *
 * @param <T> 不能为空
 * @author  create by liwuyang on 2016-07-17 16:12
 */
public interface LuminousUpdateByPrimaryKeyMapper<T> {

    /**
     * 根据主键更新实体全部字段，null值会被更新(处理乐观锁)
     *
     * @param record
     * @return
     */
    @UpdateProvider(type = LuminousBaseUpdateProvider.class, method = "dynamicSQL")
    int updateCASByPrimaryKey(T record);
}