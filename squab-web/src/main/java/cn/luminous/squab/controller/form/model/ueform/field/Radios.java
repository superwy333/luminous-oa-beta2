package cn.luminous.squab.controller.form.model.ueform.field;

import cn.luminous.squab.controller.form.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class Radios  extends FormField{

	private List<RadioOption> options ;
	
	private String value_type; //select取值类型 url:从url中获取列表,mine-自给值
	private String url ; //获取数据的url
	private String url_title_field; // 从url获取的列表数据中根据哪个字段取值
	private String url_value_field; // 从url获取的列表数据中根据哪个字段取值
	

	public List<RadioOption> getOptions() {
		return options;
	}

	public void setOptions(List<RadioOption> options) {
		this.options = options;
	}
	
	

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

	//<p>RADIO_FIELD:{|-<span leipiplugins="radios" title="RADIO_FIELD" name="RADIO_FIELD"><input name="RADIO_FIELD" value="1" checked="" type="radio"/>1&nbsp;<input name="RADIO_FIELD" value="2" type="radio"/>2&nbsp;</span>-|}</p>
	public static Radios parseText(String text) {
		Radios radio = new Radios();
		
		//拿到radio
		 String radio_regex = "<span([^<>]*?)>" ;
			
		 String[] sts = CommonUtils.maches(text, radio_regex);
		 if(sts != null &&  sts.length > 0) {
			 radio = CommonUtils.parseInputAttrsT(sts[0], Radios.class);
		 }
		 
		 String option_regex = "<input([^<>]*?)/>" ;
		 String[] ts = CommonUtils.maches(text, option_regex);
		 
		 List<RadioOption> os = new ArrayList<>();
		 
		 for(String t :ts) {
			 
			 RadioOption option = CommonUtils.parseInputAttrsT(t, RadioOption.class);
			 option.setTitle(option.getValue());
			 option.setContent(t);
			 os.add(option);
		 }
		 
		 radio.setOptions(os);
		 
		 
		return radio;
	}

	@Override
	public String getView(String value) {
		// TODO Auto-generated method stub
		

		return parseHtml(value, true);
		
		
	}
	
	private String parseHtml(String value , boolean disabled) {
		String template = getContent();
		
//		if(value == null || value.trim().length() < 1) {
//			if(disabled) {
//				template = template.replace("<span ", "<span disabled=\"disabled\" ") ;
//			}
//			  
//			return template;
//		}
		List<RadioOption> os = getOptions();
		
		
		//2018-01-30 判断是否ajax获取数据
		if(value_type != null && value_type.equals("url")) {
			//从template中替换带test的option
			for(RadioOption o : os) {
				//选中的这个值 判断checked
				//<input name="leipiNewField" value="1" checked="checked" type="checkbox"/>
				if(o.getTest() != null && o.getTest().equals("1")) 
					template = template.replace(o.getContent(), "");
				 
			}
			template = template.replace("RADIO_URL&nbsp;", "");
			
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
		                	" for(var i in data) {" ;
					
		                		if(field_value_name == null || field_value_name.trim().equals("null")) {
		                			js += " var v = data[i],t=data[i]; " ;
		                		} else {
		                			js += " var v = data[i][\"" + field_value_name + "\"] , t=data[i][\"" + field_title_name + "\"]; " ;
		                		}
		                		//2018-01-24 对于url的数据,保存后再次修改 直接选型要指向原来的
		                		// <input name="sex" value="1" type="radio" title="男">
		                		js += " 	html += '<input  type=\"radio\" name=\"" + getName() +  "\" value=\"' + v + '\" ' ;";
		                		if(value != null && value.length() > 0) {
		                			js += " if(v == '" + value + "' || data.length == 1){ " ; 
			                		js += " html += ' checked=\"checked\" ' ; " ;
		                			js += " } " ; 
		                		} else {
		                			js += " if(data.length == 1){ " ; 
			                		js += " html += ' checked=\"checked\" ' ; " ;
		                			js += " } " ; 
		                		}
		                		if(disabled) {
		                			js += " html += ' disabled=\"disabled\"' ;" ;
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
			
			for(RadioOption o : os) {
				//选中的这个值 判断checked
				//<input name="RADIO_FIELD" value="1" checked="" type="radio"/>1&nbsp;
				//<input name="RADIO_FIELD" value="2" type="radio"/>2&nbsp;
				
				String checkStr = o.getParse(o.getValue().equals(value) , disabled);
				
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
