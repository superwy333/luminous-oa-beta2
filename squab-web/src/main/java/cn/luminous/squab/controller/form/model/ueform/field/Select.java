package cn.luminous.squab.controller.form.model.ueform.field;

import cn.luminous.squab.controller.form.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * select
 * @author lyf
 *
 */
public class Select extends FormField {

	private int size ;
	 
	private String value_type; //select取值类型 url:从url中获取列表,mine-自给值
	private String url ; //获取数据的url
	private String url_title_field; // 从url获取的列表数据中根据哪个字段取值
	private String url_value_field; // 从url获取的列表数据中根据哪个字段取值
	
	private List<SelectOption> options ;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<SelectOption> getOptions() {
		return options;
	}

	public void setOptions(List<SelectOption> options) {
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

	public static Select parseText(String text) {
		
		Select select = new Select();
		
		//拿到select
		 String select_regex = "<select([^<>]*?)>" ;
			
		 String[] sts = CommonUtils.maches(text, select_regex);
		 if(sts != null &&  sts.length > 0) {
			 select = CommonUtils.parseInputAttrsT(sts[0], Select.class);
		 }
		 
		 String option_regex = "<option([^<>]*?)>(.*?)</option>" ;
		 String[] ts = CommonUtils.maches(text, option_regex);
		 
		 List<SelectOption> os = new ArrayList<>();
		 
		 for(String t :ts) {
			 
			 SelectOption option = CommonUtils.parseInputAttrsT(t, SelectOption.class);
			 option.setTitle(option.getValue());
			 //System.out.println("t : " + t);
			 option.setContent(t);
			 os.add(option);
		 }
		 
		 select.setOptions(os);
		 
		 
		return select;
	}

	@Override
	public String getView(String value) {
		// TODO Auto-generated method stub
		if(value == null || value.trim().length() < 1) {
			//如果当前没有值,判断一下默认值
			List<SelectOption> os = getOptions();
			if(os != null && value_type != null && value_type.equals("mine"))
			for(SelectOption o : os) {
				if(o.getSelected() != null && o.getSelected().trim().length() > 0) {
					value = o.getValue();
					break;
				}
			}
			
		} else {
			//如果值不为空 且选择类型为url 走ajax获取值,判断id相同的赋予title
			if(value != null && value_type != null && value_type.equals("url")) {
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
			                	" var _s_title = '';  " + 
			                	" for(var i in data) {" ;
						
			                		if(field_value_name == null || field_value_name.trim().equals("null")) {
			                			js += " var v = data[i],t=data[i]; " ;
			                		} else {
			                			js += " var v = data[i][\"" + field_value_name + "\"] , t=data[i][\"" + field_title_name + "\"]; " ;
			                		}
			                		  
			                		js += " if(v == '" + value + "'){ " ; 
			                		js += "  _s_title = t; " ;
			                		js += "  break; " ;
			                		js += " } " ;  
			                	js += " }"+// end for
			                	" $('[name=\"" + getName() + "\"]').html(_s_title); " + 
			                 " }"+ // end if
			              
			           	" }"+ //end success 
					 " });"+ //end ajax 
					
				" });"; // end $function
				  
				js += "</script>";
				
				value = js ;
				
				return "<span name=\"" + getName() + "\">" + js  + "</span>";
			}
			
		}
		 
		return super.getView(value);
	}

	@Override
	public String getEdit(String value) {
		String template = getContent();
		
		if((value == null || value.trim().length() < 1) && (value_type == null || value_type.equals("mine"))) return template;
		
		List<SelectOption> os = getOptions();
		if(os != null && value_type != null && value_type.equals("mine"))
		for(SelectOption o : os) {
			//选中的这个值 判断checked
			 //<option value="1" selected="selected">1</option><option value="2">2</option>
			String checkStr = o.getParse(o.getValue().equals(value));
			
			template = template.replace(o.getContent(), checkStr);
			
		}
		
		if(value_type != null && value_type.equals("url")) {
			
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
		                		
		                		js += " 	html += '<option value=\"' + v + '\" ' ;";
		                		if(value != null && value.length() > 0) {
		                			js += " if(v == '" + value + "'){ " ; 
			                		js += " html += 'selected=\"selected\" ' ; " ;
		                			js += " } " ; 
		                		}
		                		 
		                		js += " html += '>' + t + '</option>';"+
		                	" }"+
		                	" $('[name=\"" + getName() + "\"]').html(html); " + 
		                 " }"+
		              
		           	" }"+
				 " });"+
				
			" });";
			  
			js += "</script>";
			
			template += js ;
		}
		
	
		 
		return template;
	}
	
	
	
}
