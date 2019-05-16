package cn.luminous.squab.controller.form.model.ueform.field;

import cn.luminous.squab.controller.form.utils.CommonUtils;

/**
 * 文本
 * @author lyf
 *
 */
public class Text extends FormField {

	private String orgheight ;
	private String orgfontsize;
	private String orgalign ;// left center right
	private String orgtype ;// 文本中的内容- text,email,int,float-小数,idcard-身份证
	public String getOrgheight() {
		return orgheight;
	}
	public void setOrgheight(String orgheight) {
		this.orgheight = orgheight;
	}
	public String getOrgfontsize() {
		return orgfontsize;
	}
	public void setOrgfontsize(String orgfontsize) {
		this.orgfontsize = orgfontsize;
	}
	public String getOrgalign() {
		return orgalign;
	}
	public void setOrgalign(String orgalign) {
		this.orgalign = orgalign;
	}
	public String getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
	
	// <input name="leipiNewField" type="text" title="TEXT_FIELD" value="" leipiplugins="text" orghide="0" notnull="0" orgalign="left" orgwidth="150" orgtype="text" style="width: 150px;"/>

 
	public static FormField parseText(String text) {
		
		Text t = CommonUtils.parseInputAttrsT(text , Text.class);
		
		return t;
	}
	 
	
	
	
	
	
	
}
