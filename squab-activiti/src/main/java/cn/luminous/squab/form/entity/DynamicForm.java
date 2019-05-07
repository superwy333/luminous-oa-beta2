package cn.luminous.squab.form.entity;

import cn.luminous.squab.entity.BaseDomain;

import javax.persistence.*;

@Table(name = "dynamic_form")
public class DynamicForm extends BaseDomain {
    @Column(name = "form_name")
    private String formName;
    @Column(name = "form_code")
    private String formCode;

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

    @Column(name = "form_html")
    private String formHtml;


    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    /**
     * @return form_name
     */
    public String getFormName() {
        return formName;
    }

    /**
     * @param formName
     */
    public void setFormName(String formName) {
        this.formName = formName == null ? null : formName.trim();
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
     * @return form_html
     */
    public String getFormHtml() {
        return formHtml;
    }

    /**
     * @param formHtml
     */
    public void setFormHtml(String formHtml) {
        this.formHtml = formHtml == null ? null : formHtml.trim();
    }
}