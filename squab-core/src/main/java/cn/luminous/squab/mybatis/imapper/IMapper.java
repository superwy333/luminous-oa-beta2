package cn.luminous.squab.mybatis.imapper;


import cn.luminous.squab.mybatis.mapper.LuminousBaseInsertMapper;
import cn.luminous.squab.mybatis.mapper.LuminousBaseSelectMapper;
import cn.luminous.squab.mybatis.mapper.LuminousBaseUpdateMapper;

/**
 * 通用mapper，实现自动生成CRUD的SQL
 */
public interface IMapper<T> extends
        LuminousBaseInsertMapper<T>,
        LuminousBaseSelectMapper<T>,
        LuminousBaseUpdateMapper<T> {
}
