package cn.luminous.squab.controller.form.model.ueform.field;


import cn.luminous.squab.controller.form.utils.CommonUtils;

public class TextArea extends Text {

	private String orgrich ;

	public String getOrgrich() {
		return orgrich;
	}

	public void setOrgrich(String orgrich) {
		this.orgrich = orgrich;
	} 
	
	 
	public static TextArea parseText(String text) {
		TextArea t = CommonUtils.parseInputAttrsT(text , TextArea.class);
		
		return t;
	}
	
	@Override
	public String getEdit(String value) {
		if(value == null) {
			value = "" ;
		}
		// TODO Auto-generated method stub
		String content =  super.getEdit(value);
		
		content = content.replaceFirst(">([^<>]*?)</textarea>", ">" + value + "</textarea>");
		
		return content ;
	}
	
	public static void main(String[] args) {
		
		String textarea = "<p>address:<textarea title=\"address\" name=\"address\" leipiplugins=\"textarea\" value=\"\" orgrich=\"0\" orgfontsize=\"\" orgwidth=\"300\" orgheight=\"80\" style=\"width:300px;height:80px;\"></textarea>" ;
		
		TextArea a = new TextArea();
		a.setContent(textarea);
		String edit = a.getEdit("11111");
		
		System.out.println(edit);
		
	}
	
	
}
