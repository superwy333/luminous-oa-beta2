package cn.luminous.squab.controller.form.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

public static String[] maches(String str , String regex) {
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		List<String> list = new ArrayList<String>();
		
		while(m.find()) {
			 list.add(m.group());
			 
		}
		
		 
		
		return list.toArray(new String[0]);
	}
	
	public static JSONObject parseInputAttrs(String str) {
		
		 String preg_attr ="(\\w+)=\"(.*?)\"" ;
		 
		 List<String[]> list = machesGroup(str, preg_attr);
		 JSONObject object = new JSONObject();
		 for(String[] ss : list) {
			 object.put(ss[0], ss[1]);
		 }
		return object ;
	}
	
	public static <T> T parseInputAttrsT(String str , Class<T> t) {
		 JSONObject object = parseInputAttrs(str);
		 return object.toJavaObject(t);
		 
	}

	public static List<String[]> machesGroup(String str , String regex) {
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		List<String[]> list = new ArrayList<String[]>();
		
		while(m.find()) {
			
			int cnt = m.groupCount();
			String[] ss = new String[cnt];
			for(int i = 0 ; i< cnt ; i++) {
				ss[i] = (m.group(i + 1));
			}
			
			list.add(ss);
		}
		
		 
		
		return list ;
	}
	/**
	 * 解析form模板
	 * @param form form数据库存储实体 包含template-页面模板,data-模板json化,parse-纯浏览模式下的替换代码
	 * @param type 1-编辑模式 2-纯浏览模式 采用parse替换
	 * @param value 填充的值,可以为空
	 * @return
	 */
	/*public static String parseForm(UeForm form, int type , String value) {
		
		String template = form.getTemplate();
		String data = form.getData();
		String parse = form.getParse();
	 
		if(data == null) return "" ;
		
		//判断value是否有值
		JSONObject jsonValue = value != null ? JSON.parseObject(value) : null;
		
		
		if(type == 1) {
			//编辑模式 使用template 
			JSONArray dataValue = JSON.parseArray(data);
			int size = dataValue.size();
			//Iterator<Object> iters = dataValue.iterator();
			 
			//判断具体类型
			for(int i = 0 ; i < size ; i++) {
				JSONObject field_value = dataValue.getJSONObject(i);
				String field = field_value.getString("title");
				//该字段类型
				String plugin_type = field_value.getString("leipiplugins"); 
				
				//text select radio checkbox 先不管,主要看listctrl
				if(plugin_type != null && plugin_type.equals("listctrl")) {
					String listCtrlParse = parseListCtrl(field_value, type, jsonValue != null ? jsonValue.getJSONObject(field) : null);
				
					template = template.replace(field_value.getString("content"), listCtrlParse);
				}  

			}
			
			return template;
			
		} else if(type == 2) {
			//纯浏览模式 使用parse
			
			JSONArray dataValue = JSON.parseArray(data);
			int size = dataValue.size();
 
			
			//判断具体类型
			for(int i = 0 ; i < size ; i++) {
				JSONObject field_value = dataValue.getJSONObject(i);
				String field = field_value.getString("title");

				//该字段类型
				String plugin_type = field_value.getString("leipiplugins"); 
				
				//text select radio checkbox 先不管,主要看listctrl
				if(plugin_type != null && plugin_type.equals("listctrl")) {
					String listCtrlParse = parseListCtrl(field_value, type, jsonValue != null ? jsonValue.getJSONObject(field) : null);
				
					parse = parse.replace("{" + field + "}", listCtrlParse);
				} else {
					
					//判断是否有值 
					if(jsonValue != null && jsonValue.containsKey(field)) { 
						//parse回填
						parse = parse.replace("{" + field + "}", jsonValue.getString(field)); 
					} else {
						parse = parse.replace("{" + field + "}", ""); 
					}
					
				}

			}
			  
			return parse ;
		}
		return "";

	}
	*/
	/**
	 * 解析listctrl
	 * @param listCtrilData ueform中 data里的listctrl 整个json
	 * @param type 1-编辑模式 2-纯浏览模式 采用parse替换
	 * @param value 填充的值,可以为空
	 * @return
	 */
	/*public static String parseListCtrl(JSONObject listCtrilData , int type , JSONObject listCtrlValue) {
		JSONObject json = listCtrilData;
		
		String name = json.getString("name");
		
		String temp_html = type != 1 ? "" :"<script> " + 
		 " function tbAddRow(dname) " + 
	         "  { " + 
	         " var sTbid = dname+'_table';" + 
	         " var size = parseInt($('#' + dname + '_length').val());  " + 
	         " var fields = $('#' + dname + '_field').val();  " + 
	         " var fs =  fields.split('`'); "  + 
	         "  var table = '<tr>' ; "+ 
	         " for(var i = 0 ; i < fs.length ; i++) {"+ 
			"	 if(fs[i] && fs[i] != '') {"+
			"		 table += '<td><input class=\"input-medium\" type=\"text\" name=' + dname + '[' + (size+1) + '][' + fs[i] + ']  value=\"\"> </td>';"+
				" }" +
			"}  " + 
			"  		 table += '<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow show\">删除</a></td>';"  + 
			 "   table += '</tr>';" +
	         "  $('#' + dname + '_length').val(size+1); " +  
	         " $('#'+sTbid).append(table); " +  
	        " }" + 
	          // 删除tr
	        " function fnDeleteRow(obj)" + 
	        " {" + 
	          "   var sTbid = '"+ name +"_table';" + 
	          "   var oTable = document.getElementById(sTbid);" + 
	          "   while(obj.tagName !='TR')" + 
	           "  {" + 
	          "       obj = obj.parentNode;" + 
	           "  }" + 
	           "  oTable.deleteRow(obj.rowIndex);" + 
	           "  var size = $('#" + name + "_length').val(); " + 
	           "  $('#" + name + "_length').val(size - 1);  " +
	        " }" + 
	     "</script>"; 
		
		 String[] fields =  json.getString("orgtitle").split("`");
		  
		 
		 String table = "<table id=\"" + name +"_table\" cellspacing=\"0\" class=\"table table-bordered table-condensed\" style=\"width: 100%;\"><thead>" ;
		 table += " <tr><th colspan=\"" +  fields.length  + "\">" + name;
		 if(type == 1) {
			 table += "<span class=\"pull-right\"> "+ 
			         "<button class=\"btn btn-small btn-success\" type=\"button\" onclick=\"tbAddRow('" + name + "')\">添加一行</button>"+ 
			         "</span> ";
		 }
		
		 
		 table += "</th></tr><tr>" ;
		 for(int i = 0 ; i < fields.length ; i++) {
			 if(fields[i] != null && fields[i].length() > 0)
				 table += "<th>" + fields[i] + "</th>" ;
		 }
		 table += "</tr></thead>" ;
		 //template
		 
		 // 判断模式 
		// JSONObject  = value != null ? JSON.parseObject(value) : null;
			
		 if(type == 2) {
			  //纯查看模式
			 if(listCtrlValue != null) {
				 Set<String> keys =  listCtrlValue.keySet() ;
				 for(String key : keys) {
					 
					 JSONObject _key_v  = listCtrlValue.getJSONObject(key);
					 table += " <tr class=\"template\">";
					 //Set<String> ts = _key_v.keySet();
					 for(int i = 0 ; i < fields.length && i < _key_v.size() ; i++) {
						 if(fields[i] != null && fields[i].length() > 0)
							 table += "<td>"+ _key_v.getString(fields[i]) + "</td>" ;
					 }
					 table += " </tr>";
				 }
			 }
			
		 } else {
			 table += " <tr class=\"template\">";
			 
			 if(listCtrlValue != null) {
				 Set<String> keys =  listCtrlValue.keySet() ;
				 int index = 0;
				 for(String key : keys) {
					 
					 JSONObject _key_v  = listCtrlValue.getJSONObject(key);
					 table += " <tr class=\"template\">";
					 //Set<String> ts = _key_v.keySet();
					 for(int i = 0 ; i < fields.length && i < _key_v.size() ; i++) {
						 if(fields[i] != null && fields[i].length() > 0) 
							 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[" + index + "][" + fields[i] + "]\" value=\"" + _key_v.getString(fields[i]) + "\"> </td>" ;
							
					 }
					 table += "<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow show\">删除</a></td>" ;
						
					 table += " </tr>";
					 
					 index++;
				 }
			 } else {
				 for(int i = 0 ; i < fields.length ; i++) {
					 if(fields[i] != null && fields[i].length() > 0)
						 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[0][" + fields[i] + "]\" value=\"\"> </td>" ;
					
				 }
 
				 table += "<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow hide\">删除</a></td>" ;
				 table += " </tr>";
			 }
			 
			 
			int size = listCtrlValue != null ? listCtrlValue.size() : 0 ;
			 
			 table += "<input type=\"hidden\" id='" + name + "_length' value='" + size + "' >" ;
			 table += "<input type=\"hidden\" id='" + name + "_field' value='" + json.getString("orgtitle") + "' >" ;
		 }
		 
		 
		 
		 
		 table += " </tbody> </table>";
	 
		
		
		 String content = temp_html + "<p>" + table + "</p>" ;
	 
		
		return content ;
		
	}
	*/
	/**
	 * 解析模板
	 * @param plugin listctrl插件中的parse 代码
	 * @param template 整个原始解析模板
	 * @param _value 整个entry中的存储value,null表示没有
	 * @param type 类型 1-浏览查看 2-edit模式
	 * @return
	 
	private static String parseListCtrl(String plugin , String template , String _value , int type) {
		
		JSONObject json = JSON.parseObject(plugin);
		
		String name = json.getString("name");
		String content = json.getString("content");
		
		String $temp_html = _value != null && !content.contains("function") ? "" :"<script> " + 
		 " function tbAddRow(dname) " + 
	         "  { " + 
	         " var sTbid = dname+'_table';" + 
	         " var size = parseInt($('#' + dname + '_length').val());  " + 
	         " var fields = $('#' + dname + '_field').val();  " + 
	         " var fs =  fields.split('`'); "  + 
	         "  var table = '<tr>' ; "+ 
	         " for(var i = 0 ; i < fs.length ; i++) {"+ 
			"	 if(fs[i] && fs[i] != '') {"+
			"		 table += '<td><input class=\"input-medium\" type=\"text\" name=' + dname + '[' + (size+1) + '][' + fs[i] + ']  value=\"\"> </td>';"+
				" }" +
			"}  " + 
			"  		 table += '<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow show\">删除</a></td>';"  + 
			
			 "   table += '</tr>';" +
	         "  $('#' + dname + '_length').val(size+1); " + 
	       
	         " $('#'+sTbid).append(table); " + 
	         "   " + 
	         
//			 
//	             " $('#'+sTbid+' .template')  " + 
//	                 //连同事件一起复制    " + 
//	                "  .clone(true)    " +  
//	                 //去除模板标记    
//	                "  .removeClass('template')  " + 
//	                 //修改内部元素 " + 
//	               "   .find('.delrow').show().end()" + 
//	               "   .find('input').val('').end()" + 
//	               "   .find('textarea').val('').end()" + 
//	                 //插入表格    
//	              "   .appendTo($('#'+sTbid));" + 
	        " }" + 
	         //统计
		 "  function sum_total(dname,e){" + 
	             
	        	 " var tsum = 0;" + 
	             " $(\"input[name='\"+dname+\"[]']\").each(function(){" + 
	            		 "  var t = parseFloat($(this).val());" + 
	             "  if(!t) t=0;" + 
	                 "  if(t) tsum +=t;" + 
	                 "    $(this).val(t);" + 
	                 " }); " + 
	            " $(\"input[name='\"+dname+\"[total]']\").val(tsum);" + 

	        " }" + 

	        // 删除tr
	        " function fnDeleteRow(obj)" + 
	        " {" + 
	          "   var sTbid = '"+ name +"_table';" + 
	          "   var oTable = document.getElementById(sTbid);" + 
	          "   while(obj.tagName !='TR')" + 
	           "  {" + 
	          "       obj = obj.parentNode;" + 
	           "  }" + 
	           "  oTable.deleteRow(obj.rowIndex);" + 
	           "  var size = $('#" + name + "_length').val(); " + 
	           "  $('#" + name + "_length').val(size - 1);  " +
	        " }" + 
	     "</script>";
		
		 
//		   [{"name":"list_field"},
//		   {"leipiplugins":"listctrl"},
//		   {"type":"text"},
//		   {"value":"{列表控件}"},
//		   {"readonly":"readonly"},
//		   {"title":"list_field"},
//		   {"orgtitle":"t1`t2`t3`t4`"},
//		   {"orgcoltype":"text`text`text`text`"},
//		   {"orgunit":"````"},
//		   {"orgsum":"0`0`0`0`"},
//		   {"orgcolvalue":"````"},
//		   {"orgwidth":"100%"},
//		   {"style":"width: 100%;"}]
		 
		 String[] fields =  json.getString("orgtitle").split("`");
		// int field_length = fields.length ;
		 
		 
		 
		 String table = "<table id=\"" + name +"_table\" cellspacing=\"0\" class=\"table table-bordered table-condensed\" style=\"width: 100%;\"><thead>" ;
		 table += " <tr><th colspan=\"" +  fields.length  + "\">" + name;
		 if(_value == null) {
			 table += "<span class=\"pull-right\"> "+ 
			         "<button class=\"btn btn-small btn-success\" type=\"button\" onclick=\"tbAddRow('" + name + "')\">添加一行</button>"+ 
			         "</span> ";
		 }
		
		 
		 table += "</th></tr><tr>" ;
		 for(int i = 0 ; i < fields.length ; i++) {
			 if(fields[i] != null && fields[i].length() > 0)
				 table += "<th>" + fields[i] + "</th>" ;
		 }
		 table += "</tr></thead>" ;
		 //template
		 
		 //有值的话 这里开始循环值
		 if(_value != null) {
			 JSONObject jsonValue = JSON.parseObject(_value);
			 JSONObject listValue = jsonValue.getJSONObject(name);
			 Set<String> keys = listValue.keySet();
			 for(String key : keys) {
				 
				 JSONObject _key_v  = listValue.getJSONObject(key);
				 table += " <tr class=\"template\">";
				 //Set<String> ts = _key_v.keySet();
				 for(int i = 0 ; i < fields.length && i < _key_v.size() ; i++) {
					 if(fields[i] != null && fields[i].length() > 0)
						 table += "<td>"+ _key_v.getString(fields[i]) + "</td>" ;
				 }
				 table += " </tr>";
			 }
		 } else {
			 table += " <tr class=\"template\">";
			 for(int i = 0 ; i < fields.length ; i++) {
			
				 if(fields[i] != null && fields[i].length() > 0)
					 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[0][" + fields[i] + "]\" value=\"\"> </td>" ;
				
			 }

			
			 table += "<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow hide\">删除</a></td>" ;
			 table += " </tr>";
			 
			 table += "<input type=\"hidden\" id='" + name + "_length' value='1' >" ;
			 table += "<input type=\"hidden\" id='" + name + "_field' value='" + json.getString("orgtitle") + "' >" ;
		 }
		 
		 
		 
		 
		 table += " </tbody> </table>";
		 
//		<table id="data_1_table" cellspacing="0" class="table table-bordered table-condensed" style="width: 100%;"><thead>
//	                            <tr><th colspan="6">
//	                            采购商品列表
//	                                <span class="pull-right">
//	                                    <button class="btn btn-small btn-success" type="button" onclick="tbAddRow("data_1")">添加一行</button>
//	                                </span>
//	                            </th></tr>
//	                            <tr>
//	                              <tr><th>商品名称</th><th>数量</th><th>单价</th><th>小计</th><th>描述</th><th>操作</th></tr>
//	                            </thead>
//	                              <tr class="template">
//	                              	<td><input class="input-medium" type="text" name="data_1[0][]" value=""> </td>
//	                              	<td><input class="input-medium"  type="text" name="data_1[1][]" value=""> </td>
//	                              	<td><input class="input-medium"  type="text" name="data_1[2][]" value=""> </td>
//	                              	<td><input class="input-medium" onblur="sum_total("data_1[3]")" type="text" name="data_1[3][]" value=""> 元</td>
//	                              	<td><input class="input-medium" type="text" name="data_1[4][]" value=""> </td>
//	                                <td><a href="javascript:void(0);" onclick="fnDeleteRow(this)" class="delrow hide">删除</a></td>
//	                              </tr>
//	                            </tbody><tfooter>
//	                      <tr><td></td><td></td><td></td><td>合计：<input type="text" class="input-small" name="data_1[3][total]" onblur="sum_total("data_1[3][]")" value=""> 元</td><td></td>
//	                        <td></td>
//	                      </tr>
//	                    </tfooter></table>
//		  
		
		
		
		
		 String _content = $temp_html + "<p>" + table + "</p>" ;
		
		//System.out.println(content);
		 //parse
	     template = template.replace(content,_content);
		
		return template ;
		
	}
	*/
}
