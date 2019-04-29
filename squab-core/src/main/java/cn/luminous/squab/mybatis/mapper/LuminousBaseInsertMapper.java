package cn.luminous.squab.mybatis.mapper;

import cn.luminous.squab.mybatis.mapper.base.insert.LuminousInsertMapper;
import cn.luminous.squab.mybatis.mapper.base.insert.LuminousInsertSelectiveMapper;

public interface LuminousBaseInsertMapper<T> extends
        LuminousInsertMapper<T>,
        LuminousInsertSelectiveMapper<T> {
}
