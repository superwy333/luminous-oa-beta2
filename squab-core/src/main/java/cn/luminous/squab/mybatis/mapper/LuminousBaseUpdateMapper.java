package cn.luminous.squab.mybatis.mapper;

import cn.luminous.squab.mybatis.mapper.base.update.LuminousUpdateByPrimaryKeyMapper;
import cn.luminous.squab.mybatis.mapper.base.update.LuminousUpdateByPrimaryKeySelectiveMapper;

public interface LuminousBaseUpdateMapper<T> extends
        LuminousUpdateByPrimaryKeyMapper<T>,
        LuminousUpdateByPrimaryKeySelectiveMapper<T> {
}
