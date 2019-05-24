package cn.luminous.squab.model;

import cn.luminous.squab.entity.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

public class BizMappingModel extends BaseDomain {

    private String bizName;

    private String bizKey;

    private String processKey;

    private String formId;

    private String extField1;

    private String extField2;

    private String extField3;

    private String extField4;

    private String extField5;

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    /**
     * @return biz_key
     */
    public String getBizKey() {
        return bizKey;
    }

    /**
     * @param bizKey
     */
    public void setBizKey(String bizKey) {
        this.bizKey = bizKey == null ? null : bizKey.trim();
    }

    /**
     * @return process_key
     */
    public String getProcessKey() {
        return processKey;
    }

    /**
     * @param processKey
     */
    public void setProcessKey(String processKey) {
        this.processKey = processKey == null ? null : processKey.trim();
    }

    /**
     * @return form_id
     */
    public String getFormId() {
        return formId;
    }

    /**
     * @param formId
     */
    public void setFormId(String formId) {
        this.formId = formId == null ? null : formId.trim();
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