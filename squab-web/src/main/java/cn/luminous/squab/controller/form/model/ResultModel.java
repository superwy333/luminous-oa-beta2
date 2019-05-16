package cn.luminous.squab.controller.form.model;

import java.util.HashMap;
import java.util.Map;

public class ResultModel {

	private int status = 0; // = 0表示成功  其他异常码待定
	private String message ;
	
	private Map<String, Object> data = new HashMap<String, Object>();
	
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
	
	public void putData(String key , Object value) {
		data.put(key, value);
	}
}
