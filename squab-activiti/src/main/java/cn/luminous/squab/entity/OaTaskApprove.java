package cn.luminous.squab.entity;

import javax.persistence.Column;
import java.util.Date;

public class OaTaskApprove extends BaseDomain {

    @Column(name = "oa_task_id")
    private Long oaTaskId;
    @Column(name = "act_task_id")
    private String actTaskId;
    @Column(name = "approve_content")
    private String approveContent;
    @Column(name = "approver")
    private String approver;
    @Column(name = "approve_time")
    private Date approveTime;
    @Column(name = "ext_field1")
    private String extField1;
    @Column(name = "ext_field2")
    private String extField2;
    @Column(name = "ext_field3")
    private String extField3;
    @Column(name = "ext_field4")
    private String extField4;
    @Column(name = "ext_field5")
    private String extField5;

    public Long getOaTaskId() {
        return oaTaskId;
    }

    public void setOaTaskId(Long oaTaskId) {
        this.oaTaskId = oaTaskId;
    }

    public String getActTaskId() {
        return actTaskId;
    }

    public void setActTaskId(String actTaskId) {
        this.actTaskId = actTaskId;
    }

    public String getApproveContent() {
        return approveContent;
    }

    public void setApproveContent(String approveContent) {
        this.approveContent = approveContent;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getExtField1() {
        return extField1;
    }

    public void setExtField1(String extField1) {
        this.extField1 = extField1;
    }

    public String getExtField2() {
        return extField2;
    }

    public void setExtField2(String extField2) {
        this.extField2 = extField2;
    }

    public String getExtField3() {
        return extField3;
    }

    public void setExtField3(String extField3) {
        this.extField3 = extField3;
    }

    public String getExtField4() {
        return extField4;
    }

    public void setExtField4(String extField4) {
        this.extField4 = extField4;
    }

    public String getExtField5() {
        return extField5;
    }

    public void setExtField5(String extField5) {
        this.extField5 = extField5;
    }
}
