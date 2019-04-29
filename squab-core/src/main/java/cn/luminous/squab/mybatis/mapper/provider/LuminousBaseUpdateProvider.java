package cn.luminous.squab.mybatis.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

public class LuminousBaseUpdateProvider extends MapperTemplate {

    //继承父类的方法
    public LuminousBaseUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }
    
    /**
     * 通过主键更新全部字段
     *
     * @param ms
     */
    public String updateCASByPrimaryKey(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(updateSetColumns(entityClass, null, false, false));
        sql.append(wherePKAndVersionColumns(entityClass));
        return sql.toString();
    }

    /**
     * 通过主键更新不为null的字段
     *
     * @param ms
     * @return
     */
    public String updateCASByPrimaryKeySelective(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(updateSetColumns(entityClass, null, true, isNotEmpty()));
        sql.append(wherePKAndVersionColumns(entityClass));
        return sql.toString();
    }
    
    /**
     * update set列
     *
     * @param entityClass
     * @param entityName  实体映射名
     * @param notNull     是否判断!=null
     * @param notEmpty    是否判断String类型!=''
     * @return
     */
    public static String updateSetColumns(Class<?> entityClass, String entityName, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isId() && column.isUpdatable()) {
                if("version".equalsIgnoreCase(column.getColumn())){
                    sql.append("version = version + 1 ,");
                }else if (notNull) {
                    sql.append(SqlHelper.getIfNotNull(entityName, column, column.getColumnEqualsHolder(entityName) + ",", notEmpty));
                } else {
                    sql.append(column.getColumnEqualsHolder(entityName) + ",");
                }
            }
        }
        sql.append("</set>");
        return sql.toString();
    }

    /**
     * where主键条件
     *
     * @param entityClass
     * @return
     */
    public static String wherePKAndVersionColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if(column.isId()){
                sql.append(" AND " + column.getColumnEqualsHolder());
            }else if("version".equalsIgnoreCase(column.getColumn())){
                sql.append(" AND " + column.getColumnEqualsHolder());
            }
        }
        sql.append("</where>");
        return sql.toString();
    }
}
