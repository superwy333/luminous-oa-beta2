package cn.luminous.squab.model;

import cn.luminous.squab.entity.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

public class DepartmentModel extends BaseDomain {
    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门描述
     */
    private String intro;

    /**
     * 负责人
     */
    private String charge;

    /**
     * 上级部门
     */
    private Integer pid;

    /**
     * 排序
     */
    private Integer sort;

    private String leader;

    private String leaderTxt;

    /**
     * 分管领导 多个用,号隔开
     */
    private String leaderBranch;

    /**
     * 分管领导名单
     */
    private String leaderBranchTxt;

    private Long parentLeader;

    private String parentLeaderTxt;

    /**
     * 可用状态
     */
    private Boolean status;

    private String createdAt;

    private Date updatedAt;

    private String deletedAt;

    private String extField1;

    private String extField2;

    private String extField3;

    private String extField4;

    private String extField5;

    /**
     * 获取部门名称
     *
     * @return name - 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置部门名称
     *
     * @param name 部门名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取部门描述
     *
     * @return intro - 部门描述
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 设置部门描述
     *
     * @param intro 部门描述
     */
    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    /**
     * 获取负责人
     *
     * @return charge - 负责人
     */
    public String getCharge() {
        return charge;
    }

    /**
     * 设置负责人
     *
     * @param charge 负责人
     */
    public void setCharge(String charge) {
        this.charge = charge == null ? null : charge.trim();
    }

    /**
     * 获取上级部门
     *
     * @return pid - 上级部门
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置上级部门
     *
     * @param pid 上级部门
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return leader
     */
    public String getLeader() {
        return leader;
    }

    /**
     * @param leader
     */
    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    /**
     * @return leader_txt
     */
    public String getLeaderTxt() {
        return leaderTxt;
    }

    /**
     * @param leaderTxt
     */
    public void setLeaderTxt(String leaderTxt) {
        this.leaderTxt = leaderTxt == null ? null : leaderTxt.trim();
    }

    /**
     * 获取分管领导 多个用,号隔开
     *
     * @return leader_branch - 分管领导 多个用,号隔开
     */
    public String getLeaderBranch() {
        return leaderBranch;
    }

    /**
     * 设置分管领导 多个用,号隔开
     *
     * @param leaderBranch 分管领导 多个用,号隔开
     */
    public void setLeaderBranch(String leaderBranch) {
        this.leaderBranch = leaderBranch == null ? null : leaderBranch.trim();
    }

    /**
     * 获取分管领导名单
     *
     * @return leader_branch_txt - 分管领导名单
     */
    public String getLeaderBranchTxt() {
        return leaderBranchTxt;
    }

    /**
     * 设置分管领导名单
     *
     * @param leaderBranchTxt 分管领导名单
     */
    public void setLeaderBranchTxt(String leaderBranchTxt) {
        this.leaderBranchTxt = leaderBranchTxt == null ? null : leaderBranchTxt.trim();
    }

    /**
     * @return parent_leader
     */
    public Long getParentLeader() {
        return parentLeader;
    }

    /**
     * @param parentLeader
     */
    public void setParentLeader(Long parentLeader) {
        this.parentLeader = parentLeader;
    }

    /**
     * @return parent_leader_txt
     */
    public String getParentLeaderTxt() {
        return parentLeaderTxt;
    }

    /**
     * @param parentLeaderTxt
     */
    public void setParentLeaderTxt(String parentLeaderTxt) {
        this.parentLeaderTxt = parentLeaderTxt == null ? null : parentLeaderTxt.trim();
    }

    /**
     * 获取可用状态
     *
     * @return status - 可用状态
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置可用状态
     *
     * @param status 可用状态
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return created_at
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt == null ? null : createdAt.trim();
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
    public String getDeletedAt() {
        return deletedAt;
    }

    /**
     * @param deletedAt
     */
    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt == null ? null : deletedAt.trim();
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
}