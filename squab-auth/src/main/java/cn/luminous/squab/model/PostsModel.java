package cn.luminous.squab.model;

import cn.luminous.squab.entity.BaseDomain;

import javax.persistence.Column;
import java.util.Date;

public class PostsModel extends BaseDomain {
    /**
     * 岗位名称
     */
    private String name;

    private String postCode;

    /**
     * 岗位描述
     */
    private String intro;

    /**
     * 上级
     */
    private Integer pid;

    /**
     * 等级 高级排序从小到大
     */
    private Integer rank;

    /**
     * 岗位状态
     */
    private Boolean status;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private String extField1;

    private String extField2;

    private String extField3;

    private String extField4;

    private String extField5;

    /**
     * 权限,json格式
     */
    private String purview;

    /**
     * 获取岗位名称
     *
     * @return name - 岗位名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置岗位名称
     *
     * @param name 岗位名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return post_code
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postCode
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    /**
     * 获取岗位描述
     *
     * @return intro - 岗位描述
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 设置岗位描述
     *
     * @param intro 岗位描述
     */
    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    /**
     * 获取上级
     *
     * @return pid - 上级
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置上级
     *
     * @param pid 上级
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取等级 高级排序从小到大
     *
     * @return rank - 等级 高级排序从小到大
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * 设置等级 高级排序从小到大
     *
     * @param rank 等级 高级排序从小到大
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * 获取岗位状态
     *
     * @return status - 岗位状态
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置岗位状态
     *
     * @param status 岗位状态
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return deleted_at
     */
    public Date getDeletedAt() {
        return deletedAt;
    }

    /**
     * @param deletedAt
     */
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * @return ext_field1
     */
    public String getExtField1() {
        return extField1;
    }

    /**
     * @param extField1
     */
    public void setExtField1(String extField1) {
        this.extField1 = extField1 == null ? null : extField1.trim();
    }

    /**
     * @return ext_field2
     */
    public String getExtField2() {
        return extField2;
    }

    /**
     * @param extField2
     */
    public void setExtField2(String extField2) {
        this.extField2 = extField2 == null ? null : extField2.trim();
    }

    /**
     * @return ext_field3
     */
    public String getExtField3() {
        return extField3;
    }

    /**
     * @param extField3
     */
    public void setExtField3(String extField3) {
        this.extField3 = extField3 == null ? null : extField3.trim();
    }

    /**
     * @return ext_field4
     */
    public String getExtField4() {
        return extField4;
    }

    /**
     * @param extField4
     */
    public void setExtField4(String extField4) {
        this.extField4 = extField4 == null ? null : extField4.trim();
    }

    /**
     * @return ext_field5
     */
    public String getExtField5() {
        return extField5;
    }

    /**
     * @param extField5
     */
    public void setExtField5(String extField5) {
        this.extField5 = extField5 == null ? null : extField5.trim();
    }

    /**
     * 获取权限,json格式
     *
     * @return purview - 权限,json格式
     */
    public String getPurview() {
        return purview;
    }

    /**
     * 设置权限,json格式
     *
     * @param purview 权限,json格式
     */
    public void setPurview(String purview) {
        this.purview = purview == null ? null : purview.trim();
    }
}