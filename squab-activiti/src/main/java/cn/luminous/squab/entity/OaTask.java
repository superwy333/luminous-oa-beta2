package cn.luminous.squab.entity;


import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "oa_task")
public class OaTask extends BaseDomain {

    @Column(name = "biz_key")
    private String bizKey;
    @Column(name = "data")
    private String data;
    @Column(name = "task_state")
    private String taskState;
    @Column(name = "proc_inst_id")
    private String procInstId;
    @Column(name = "proc_def_id")
    private String procDefId;
    @Column(name = "proc_key")
    private String processKey;
    @Column(name = "apply_name")
    private String applyName;
    @Column(name = "apply_code")
    private String applyCode;
    @Column(name = "apply_time")
    private Date applyTime;
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

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
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
