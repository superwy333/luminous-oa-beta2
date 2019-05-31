package cn.luminous.squab.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_conf_dictitem")
public class SysConfDictitem extends BaseDomain {
    /**
     * 条目名称
     */
    @Column(name = "dic_item_name")
    private String dicItemName;

    /**
     * 条目代码
     */
    @Column(name = "dic_item_level")
    private String dicItemLevel;

    /**
     * 条目代码
     */
    @Column(name = "dic_item_parent")
    private String dicItemParent;

    /**
     * 条目代码
     */
    @Column(name = "dic_item_code")
    private String dicItemCode;

    /**
     * 条目值
     */
    @Column(name = "dic_item_value")
    private String dicItemValue;

    /**
     * 条目描述
     */
    @Column(name = "dic_item_desc")
    private String dicItemDesc;

    /**
     * 启用状态 0:未启用；1：启用（默认）；
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

    /**
     * 获取条目名称
     *
     * @return dic_item_name - 条目名称
     */
    public String getDicItemName() {
        return dicItemName;
    }

    /**
     * 设置条目名称
     *
     * @param dicItemName 条目名称
     */
    public void setDicItemName(String dicItemName) {
        this.dicItemName = dicItemName == null ? null : dicItemName.trim();
    }

    /**
     * 获取条目代码
     *
     * @return dic_item_level - 条目代码
     */
    public String getDicItemLevel() {
        return dicItemLevel;
    }

    /**
     * 设置条目代码
     *
     * @param dicItemLevel 条目代码
     */
    public void setDicItemLevel(String dicItemLevel) {
        this.dicItemLevel = dicItemLevel == null ? null : dicItemLevel.trim();
    }

    /**
     * 获取条目代码
     *
     * @return dic_item_parent - 条目代码
     */
    public String getDicItemParent() {
        return dicItemParent;
    }

    /**
     * 设置条目代码
     *
     * @param dicItemParent 条目代码
     */
    public void setDicItemParent(String dicItemParent) {
        this.dicItemParent = dicItemParent == null ? null : dicItemParent.trim();
    }

    /**
     * 获取条目代码
     *
     * @return dic_item_code - 条目代码
     */
    public String getDicItemCode() {
        return dicItemCode;
    }

    /**
     * 设置条目代码
     *
     * @param dicItemCode 条目代码
     */
    public void setDicItemCode(String dicItemCode) {
        this.dicItemCode = dicItemCode == null ? null : dicItemCode.trim();
    }

    /**
     * 获取条目值
     *
     * @return dic_item_value - 条目值
     */
    public String getDicItemValue() {
        return dicItemValue;
    }

    /**
     * 设置条目值
     *
     * @param dicItemValue 条目值
     */
    public void setDicItemValue(String dicItemValue) {
        this.dicItemValue = dicItemValue == null ? null : dicItemValue.trim();
    }

    /**
     * 获取条目描述
     *
     * @return dic_item_desc - 条目描述
     */
    public String getDicItemDesc() {
        return dicItemDesc;
    }

    /**
     * 设置条目描述
     *
     * @param dicItemDesc 条目描述
     */
    public void setDicItemDesc(String dicItemDesc) {
        this.dicItemDesc = dicItemDesc == null ? null : dicItemDesc.trim();
    }

    /**
     * 获取启用状态 0:未启用；1：启用（默认）；
     *
     * @return is_enabled - 启用状态 0:未启用；1：启用（默认）；
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置启用状态 0:未启用；1：启用（默认）；
     *
     * @param isEnabled 启用状态 0:未启用；1：启用（默认）；
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }
}