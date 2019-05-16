package cn.luminous.squab.controller.form.model.ueform.field;

public class SelectOption {

	private String value ;
	private String title;
	private String selected ;
	private String content ;
	private String name ;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	 
	public String getParse(boolean selected) {
		//<option value="1" selected="selected">1</option>
		String str = "<option name=\"" + getName() + "\" value=\"" + getValue() + "\"  " ;
		if(selected) {
			str += " selected=\"selected\" " ;
		}
		str += ">" + getTitle() + "</option>" ;
		  
		return str ;
	}
	
	
}
