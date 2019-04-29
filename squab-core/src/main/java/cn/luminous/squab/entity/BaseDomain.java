package cn.luminous.squab.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: BaseDomain
 * @Description: 实体基础类
 * @date 2016年5月19日 上午1:46:11
 * 
 */
@MappedSuperclass
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = -6575246587984645368L;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 上一次修改人
     */
    @Column(name = "last_modify_by")
    private String lastModifyBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 上一次修改时间
     */
    @Column(name = "last_modify_time")
    private Date lastModifyTime;

    /** 版本号（乐观锁）  严禁设置默认值 */
    @Column(name = "version")
    private Long version;

    /**
     * 是否删除 （0：未删除；1：已删除；） 默认0
     */
    private Integer deleted = 0;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getLastModifyBy() {
        return this.lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
