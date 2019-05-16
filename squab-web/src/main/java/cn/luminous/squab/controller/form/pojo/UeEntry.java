package cn.luminous.squab.controller.form.pojo;

import java.util.Date;

/**
 * 数据条目
 * @author lyf
 *
 */
public class UeEntry {

	private Integer id ;//	int primary key auto_increment ,
	private Integer form_id ;//	int ,
	//private String entry_name ;// varchar(200),
	private Date crtime  ;// timestamp ,
	private Date  modify_time	 ;// timestamp 
	private String value ;
	
	//冗余一个字段 form_name
	private String form_name ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getForm_id() {
		return form_id;
	}
	public void setForm_id(Integer form_id) {
		this.form_id = form_id;
	}
 
	public Date getCrtime() {
		return crtime;
	}
	public void setCrtime(Date crtime) {
		this.crtime = crtime;
	}
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getForm_name() {
		return form_name;
	}
	public void setForm_name(String form_name) {
		this.form_name = form_name;
	}
	
	
	
	
	
}
