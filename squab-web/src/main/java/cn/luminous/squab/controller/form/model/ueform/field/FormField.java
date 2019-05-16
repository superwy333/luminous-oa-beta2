package cn.luminous.squab.controller.form.model.ueform.field;

import cn.luminous.squab.controller.form.utils.CommonUtils;
import com.alibaba.fastjson.JSONObject;

public class FormField {

	private String leipiplugins ;// -- 控件类型  text,listctrl,select,radio....
	private String type ;// --text
	private String title ;
	private String content ; // 具体的html该字段内容
	private String notnull ;// 是否要判断非空 1为必须判断非空
	private String orgwidth ; // 宽度 按照css样式走
	private String style ;// 附加样式
	private String name ;
	private String value ;// 默认值
	
	public String getLeipiplugins() {
		return leipiplugins;
	}
	public void setLeipiplugins(String leipiplugins) {
		this.leipiplugins = leipiplugins;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	 
	public String getNotnull() {
		return notnull;
	}
	public void setNotnull(String notnull) {
		this.notnull = notnull;
	}
	public String getOrgwidth() {
		return orgwidth;
	}
	public void setOrgwidth(String orgwidth) {
		this.orgwidth = orgwidth;
	}
	public String getStyle() {
		if(style == null) return "" ;
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 视图展示页面解析  如果当前value有值 需要把value的值带入
	 * @return
	 */
	public  String getView(String value){
		 
		if(value == null) {
			value = "" ;
		}
		
//		String str = "<div name='" + getName() + "' title='" + getTitle() + "' style='" + getStyle() + "' value='" + value + "' "
//					+ ">" + value + "</div>";
		 String str = value;
		return str;
	} 
	
	/**
	 * 编辑页面解析  如果当前value有值需要把value的值带入
	 * @return
	 */
	public String getEdit(String value) {
		if(value == null) {
			value = "" ;
		}
		
		String val_str = "value=\"" + value + "\"" ;
		//"value=\"([^\"]*?)\""
		String str = getContent().replaceFirst("value=\"([^\"]*?)\"", val_str);
		return str;
	}
	
	/**
	 * 根据html文本内容解析为具体的类型实体
	 * @param text
	 * @return
	 */
	public static FormField parseText(String text) {
		
		text = text.replace("|-", "").replace("-|", "");
		
		 JSONObject attrs = CommonUtils.parseInputAttrs(text);
		 String tag = attrs.getString("leipiplugins");
		 
		 FormField ff = null;
		 if(tag.equals("text")) {
			 ff = Text.parseText(text);
		 } else if(tag.equals("textarea")) {
			 ff = TextArea.parseText(text);
		 } else if(tag.equals("select")) {
			 ff = Select.parseText(text);
		 } else if(tag.equals("radios")) {
			 ff = Radios.parseText(text);
		 } else if(tag.equals("checkboxs")) {
			 ff = Checkboxs.parseText(text);
		 } else if(tag.equals("listctrl")) {
			 ff = ListCtrl.parseText(text);
		 } else if(tag.equals("datepicker")) {
			 ff = DatePicker.parseText(text);
		 }
		 
		 if(ff != null){
			 ff.setContent(text);
		 }
			
		 
		 
		 return ff ;
		
	}
	
	
}
