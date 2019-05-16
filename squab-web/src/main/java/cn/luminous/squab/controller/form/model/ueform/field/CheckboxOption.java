package cn.luminous.squab.controller.form.model.ueform.field;

import java.util.List;

public class CheckboxOption {

	private String name ;
	private String value ;
	private String checked ;
	private String type ;
	private String title ; //  为空则用name
	private String content ;
	private String test ;
	
	public void setTest(String test) {
		this.test = test;
	}
	public String getTest() {
		return test;
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
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getParse(List<String> values , boolean disabled) {
		if(test != null && test.equals("1")) return "" ;
		
		String str = "<input name=\"" + getName() + "\" value=\"" + getValue() + "\" type=\"checkbox\" " ;
		
		if((values == null && getChecked() != null) || (values != null && values.contains(getValue())) ) {
			str += " checked=\"checked\" " ;
		}
		 
		if(disabled) {
			str += " disabled=\"disabled\" " ;
		}
		
		str += "/>" ;
	
		return str ;
	}
	
}
