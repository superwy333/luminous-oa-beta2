package cn.luminous.squab.controller.form.model.ueform.field;


import cn.luminous.squab.controller.form.utils.CommonUtils;

public class DatePicker extends FormField {

	private String now;
	
	public void setNow(String now) {
		this.now = now;
	}
	public String getNow() {
		return now;
	}
	
	public static DatePicker parseText(String text) {
		DatePicker t = CommonUtils.parseInputAttrsT(text , DatePicker.class);
		
		return t;
	}
	
	@Override
	public String getEdit(String value) {
		
		/*<input size="16" type="text" value="2012-06-15 14:45" readonly class="form_datetime">
		 
		<script type="text/javascript">
		    $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
		</script> */
		
//		String js = "<script> "+
//				"  $('[name=\"" + getName() + "\"]').datepicker({ language:\"zh-CN\", format:'yyyy-mm-dd',defaultDate:+0}); ";
//
//		if(value == null && getNow() != null && getNow().equals("1")) {
//			//赋值
//			value = "today" ;
//			js += " $('[name=\"" + getName() + "\"]').datepicker('setDate', new Date());";
//		} else if(value != null) {
//			js += " $('[name=\"" + getName() + "\"]').datepicker('setDate', '" + value + "');";
//		}
//
//		js += "</script>" ;

		String js = "<script>";
		js += "  $('[name=\"" + getName() + "\"]').jeDate({format: \"YYYY-MM-DD\"})";
		js += "</script>";
		
		String content = super.getEdit(value);
		
		content += js ;
		
		return content;
	}
	
}
