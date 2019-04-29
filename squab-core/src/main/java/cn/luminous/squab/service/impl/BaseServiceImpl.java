package cn.luminous.squab.service.impl;

import cn.luminous.squab.entity.BaseDomain;
import cn.luminous.squab.mybatis.imapper.IMapper;
import cn.luminous.squab.service.BaseService;

import javax.persistence.OptimisticLockException;
import java.util.Date;
import java.util.List;

public abstract class BaseServiceImpl<T extends BaseDomain> implements BaseService<T> {

    protected abstract IMapper<T> getBaseMapper();

    @Override
    public List<T> query(T record) {
        return this.getBaseMapper().select(record);
    }

    @Override
    public T queryById(Long id) {
        return this.getBaseMapper().selectByPrimaryKey(id);
    }

    @Override
    public T queryOne(T record) {
        return this.getBaseMapper().selectOne(record);
    }

    @Override
    public int add(T record) {
        //TODO 下面的当前用户信息等集成shrio之后再加上
        //record.setCreateBy(getCurrentUser(record));
        //record.setLastModifyBy(getCurrentUser(record));
        record.setVersion(0L);
        Date currentDate = new Date();
        record.setCreateTime(currentDate);
        record.setLastModifyTime(currentDate);
        int count = this.getBaseMapper().insert(record);
        if (count > 0) {
            record.setVersion(1L); // 新增成功默认为1
        }
        return count;
    }

    @Override
    public int addSelective(T record) {
        //record.setCreateBy(getCurrentUser(record));
        //record.setLastModifyBy(getCurrentUser(record));
        record.setVersion(0L);
        Date currentDate = new Date();
        record.setCreateTime(currentDate);
        record.setLastModifyTime(currentDate);
        int count = this.getBaseMapper().insertSelective(record);
        if (count > 0) {
            record.setVersion(1L); // 新增成功默认为1
        }
        return count;
    }

    @Override
    public int updateById(T record) {
        //record.setLastModifyBy(getCurrentUser(record));
        record.setLastModifyTime(new Date());
        int count = this.getBaseMapper().updateCASByPrimaryKey(record);
        if (count <= 0) {
            throw new OptimisticLockException("根据ID更新整个实体对象时乐观锁版本不一致，更新失败。");
        } else {
            long version = record.getVersion() + 1L;
            record.setVersion(version);
        }
        return count;
    }

    @Override
    public int updateByIdSelective(T record) {
        //record.setLastModifyBy(getCurrentUser(record));
        record.setLastModifyTime(new Date());
        int count = this.getBaseMapper().updateCASByPrimaryKeySelective(record);
        if (count <= 0) {
            throw new OptimisticLockException("根据ID更新实体对象指定字段时乐观锁版本不一致，更新失败。");
        } else {
            long version = record.getVersion() + 1L;
            record.setVersion(version);
        }
        return count;
    }

    @Override
    public int remove(T record) {
        record.setDeleted(1); // 删除标记 1：已删除
        //record.setLastModifyBy(getCurrentUser(record));
        record.setLastModifyTime(new Date());
        int count = this.getBaseMapper().updateCASByPrimaryKey(record);
        if (count <= 0) {
            throw new OptimisticLockException("根据ID逻辑删除实体对象时乐观锁版本不一致，逻辑删除失败。");
        } else {
            long version = record.getVersion() + 1L;
            record.setVersion(version);
        }
        return count;
    }
}
