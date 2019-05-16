package cn.luminous.squab.controller.form.pojo;

import cn.luminous.squab.controller.form.model.ueform.field.FormField;
import cn.luminous.squab.controller.form.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UeForm {
	private Integer form_id ;//	int primary key auto_increment ,
	private String form_name;//	varchar(255),
	private String template;// text , 原始页面过来的编辑页面
	private String html; //解析过可以review的html代码 跑
	private String data	;//text ,
	private String parse;//	text , 解析过的 可以做为view的代码
	private Integer fields;//	int ,
	//private String add_fields;//	text 
	private Date crtime ;
	private Date modify_time ;
	
	public Integer getForm_id() {
		return form_id;
	}
	public void setForm_id(Integer form_id) {
		this.form_id = form_id;
	}
	public String getForm_name() {
		return form_name;
	}
	public void setForm_name(String form_name) {
		this.form_name = form_name;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	 
	public String getParse() {
		return parse;
	}
	public void setParse(String parse) {
		this.parse = parse;
	}
	public Integer getFields() {
		return fields;
	}
	public void setFields(Integer fields) {
		this.fields = fields;
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
	 
	public String getHtml() {
		if(html.contains("{|-"))
			html = html.replace("{|-", "").replace("-|}", "");
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public static UeForm praseTemplate(String template) {
		
		UeForm form = new UeForm();

		String preg =  "(\\|-<span(((?!<span).)*leipiplugins=\"(radios|checkboxs|select)\".*?)>(.*?)<\\/span>-\\||<(img|input|textarea|select).*?(<\\/select>|<\\/textarea>|\\/>))";

		String _html = template.replace("{|-", "").replace("-|}", "");
		String _parse = _html ;
		
		
		List<FormField> fs = new ArrayList<>();

		String[] ms = CommonUtils.maches(template, preg);
		for(String m : ms) {
 
			FormField ff = FormField.parseText(m);
			fs.add(ff);
			
			if(ff.getLeipiplugins().equals("listctrl") || ff.getLeipiplugins().equals("select") || ff.getLeipiplugins().equals("datepicker")
					|| ff.getLeipiplugins().equals("radios") || ff.getLeipiplugins().equals("checkboxs")) {
				//要替换listctrl
				String content = ff.getEdit(ff.getValue());
				_html = _html.replace(ff.getContent(), content);
			
			}
			String _f_parse = ff.getView(ff.getValue());
			if(_f_parse != null)
				_parse = _parse.replace(ff.getContent(), _f_parse);
			
  
		}
		
		
		
		form.setForm_fields(fs);
		form.setData(JSON.toJSONString(fs));
		form.setFields(fs.size());
		form.setParse(_parse);
		form.setTemplate(template);
		form.setHtml(_html);
		
		return form ;
	}
	
	
	public String getEdit(String value) {

		String _temp = getTemplate().replace("{|-", "").replace("-|}", "");
		if(value == null) return _temp;
		
		
		JSONObject jsonObjet = JSON.parseObject(value);
		
		List<FormField> ffs = getForm_fields();
		for(FormField ff : ffs) { 
			
			String ff_value = jsonObjet.getString(ff.getName());
			 
			String ff_view = ff.getEdit(ff_value);
			
			_temp = _temp.replace(ff.getContent(), ff_view);
			  
		}
		 
		return _temp;
	}
	
	/**
	 * 视图页面展示
	 * @param value 后台保存的当前form的值 json
	 * @return 页面html代码
	 */
	public String getView(String value) {
		
		if(value == null) return getParse();
		
		String _temp = getTemplate().replace("{|-", "").replace("-|}", "");
		
		JSONObject jsonObjet = JSON.parseObject(value);
		
		List<FormField> ffs = getForm_fields() ;
		for(FormField ff : ffs) { 
			
			String ff_value = jsonObjet.getString(ff.getName());
			 
			String ff_view = ff.getView(ff_value);
			
			_temp = _temp.replace(ff.getContent(), ff_view);
			  
		}
		 
		return _temp;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		 
		this.data = data;
	}
	
	private List<FormField> form_fields ;
	
	public void setForm_fields(List<FormField> form_fields) {
		this.form_fields = form_fields;
	}
	public List<FormField> getForm_fields() {
		
		if(data != null && (form_fields == null)) {
			List<FormField> list = JSON.parseArray(data , FormField.class);
			form_fields = new ArrayList<>();
			for(FormField ff : list) {
				ff = FormField.parseText(ff.getContent());
				form_fields.add(ff);
			}
		}
		
		return form_fields;
	}
	
	 
	
	
}
