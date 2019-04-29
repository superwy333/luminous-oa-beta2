package cn.luminous.squab.mybatis.mapper;

import cn.luminous.squab.mybatis.mapper.base.select.LuminousSelectByPrimaryKeyMapper;
import cn.luminous.squab.mybatis.mapper.base.select.LuminousSelectMapper;
import cn.luminous.squab.mybatis.mapper.base.select.LuminousSelectOneMapper;

public interface LuminousBaseSelectMapper<T> extends
        LuminousSelectMapper<T>,
        LuminousSelectOneMapper<T>,
        LuminousSelectByPrimaryKeyMapper<T> {
}
