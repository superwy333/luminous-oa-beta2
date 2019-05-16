package cn.luminous.squab.controller.form.model.ueform.field;

import com.alibaba.fastjson.JSON;
import cn.luminous.squab.controller.form.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class Checkboxs extends FormField{

	private List<CheckboxOption> options ;
	
	private String value_type; //select取值类型 url:从url中获取列表,mine-自给值
	private String url ; //获取数据的url
	private String url_title_field; // 从url获取的列表数据中根据哪个字段取值
	private String url_value_field; // 从url获取的列表数据中根据哪个字段取值


	public List<CheckboxOption> getOptions() {
		return options;
	}

	public void setOptions(List<CheckboxOption> options) {
		this.options = options;
	}
	
	 
	//<p>CHECKBOX_FIELD:{|-<span leipiplugins="checkboxs" title="CHECKBOX_FIELD"><input name="leipiNewField" value="1" checked="checked" type="checkbox"/>1&nbsp;<input name="leipiNewField" value="2" checked="checked" type="checkbox"/>2&nbsp;<input name="leipiNewField" value="3" type="checkbox"/>3&nbsp;</span>-|}</p>

	public String getValue_type() {
		return value_type;
	}

	public void setValue_type(String value_type) {
		this.value_type = value_type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl_title_field() {
		return url_title_field;
	}

	public void setUrl_title_field(String url_title_field) {
		this.url_title_field = url_title_field;
	}

	public String getUrl_value_field() {
		return url_value_field;
	}

	public void setUrl_value_field(String url_value_field) {
		this.url_value_field = url_value_field;
	}

	public static Checkboxs parseText(String text) {
		Checkboxs checkbox = new Checkboxs();
		
		//拿到checkbox
		 String checkbox_regex = "<span([^<>]*?)>" ;
			
		 String[] sts = CommonUtils.maches(text, checkbox_regex);
		 if(sts != null &&  sts.length > 0) {
			 checkbox = CommonUtils.parseInputAttrsT(sts[0], Checkboxs.class);
		 }
		 
		 String option_regex = "<input([^<>]*?)/>" ;
		 String[] ts = CommonUtils.maches(text, option_regex);
		 
		 List<CheckboxOption> os = new ArrayList<>();
		 
		 for(String t :ts) {
			 
			 CheckboxOption option = CommonUtils.parseInputAttrsT(t, CheckboxOption.class);
			 option.setTitle(option.getValue());
			// System.out.println("checkbox : " + t);
			 option.setContent(t);
			 os.add(option);
		 }
		 
		 checkbox.setOptions(os);
//		 checkbox.setName(checkbox.getTitle());
		 
		 
		return checkbox;
	}

	@Override
	public String getView(String value) {
		
		//<input name="leipiNewField" value="1" checked="checked" type="checkbox"/>
		 
		return parseHtml(value, true);
	}

	private String parseHtml(String value , boolean readonly) {
		String template = getContent();
		 
		List<CheckboxOption> os = getOptions();
		
		
		
		if(value_type != null && value_type.equals("url")) {
			
			//从template中替换带test的option
			for(CheckboxOption o : os) {
				//选中的这个值 判断checked
				//<input name="leipiNewField" value="1" checked="checked" type="checkbox"/>
				if(o.getTest() != null && o.getTest().equals("1")) 
					template = template.replace(o.getContent(), "");
				
			}

			template = template.replace("CHECKBOXS_URL&nbsp;", "");
			
			String url = getUrl();
			String field_value_name = getUrl_value_field();
			String field_title_name = getUrl_title_field();
			
			//拼js
			String js = "<script>" ; 
			js += "$(function(){ "+
					" $.ajax({"+
					 "  type: 'GET',"+
		             " url : '" + url + "' , "+
		             " success : function(data){"+
					 	" if(data && data.length > 0){"+
		                	" var html = '' ;"+
		                	" var _vals = " + ( value != null && value.length() > 0 && !value.equals("[]") ? value : "[]") + " ;" +
		                	" for(var i in data) {" ;
					
		                		if(field_value_name == null || field_value_name.trim().equals("null")) {
		                			js += " var v = data[i],t=data[i]; " ;
		                		} else {
		                			js += " var v = data[i][\"" + field_value_name + "\"] , t=data[i][\"" + field_title_name + "\"]; " ;
		                		}
		                		//2018-01-24 对于url的数据,保存后再次修改 直接选型要指向原来的
		                		// <input name="sex" value="1" type="radio" title="男">
		                		js += " 	html += '<input  type=\"checkbox\" name=\"" + getName() +  "\" value=\"' + v + '\" ' ;";
		                		if(value != null && value.length() > 0 && !value.equals("[]")) {
		                			js += " for(var j in _vals){ " ; 
		                			js += " if(v == _vals[j] ) {" ;
			                		js += " html += ' checked=\"checked\" ' ; " ;
			                		js += " break ;}" ;
		                			js += " } " ; 
		                		} 
		                		if(readonly) {
		                			js += " html += ' disabled=\"disabled\" ' ;" ;
		                		}
		                		 
		                		js += " html += '/>' + t + '&nbsp;' ;"+
		                	" }"+
		                	" $('[name=\"" + getName() + "\"]').html(html); " + 
		                 " }"+
		              
		           	" }"+
				 " });"+
				
			" });";
			  
			js += "</script>";
			
			return template + js ;
			
		} else {
			List<String> jsonValues = null;
			if(value != null && value.trim().length() > 1)
				jsonValues = JSON.parseArray(value, String.class);
			
			
			for(CheckboxOption o : os) {
				//选中的这个值 判断checked
				//<input name="leipiNewField" value="1" checked="checked" type="checkbox"/>
				
				String checkStr = o.getParse(jsonValues, readonly);
				
				template = template.replace(o.getContent(), checkStr);
				
			}
			
			return template;
		}
		
		
		
	}

	@Override
	public String getEdit(String value) {
		return parseHtml(value, false);
		 
	}
	
	
	
}
