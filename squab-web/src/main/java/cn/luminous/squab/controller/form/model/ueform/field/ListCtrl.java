package cn.luminous.squab.controller.form.model.ueform.field;

import cn.luminous.squab.controller.form.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Set;

public class ListCtrl extends FormField {

    private String orgtitle; // "T1`T2`T3`"
    private String orgcoltype; //"text`text`text`"
    private String orgunit;// "```"
    private String orgsum;//"0`0`0`"
    private String orgcolvalue;//"```"
    private String orgkey; // 实际存储每个列需要的key

    public String getOrgtitle() {
        return orgtitle;
    }

    public void setOrgtitle(String orgtitle) {
        this.orgtitle = orgtitle;
    }

    public String getOrgcoltype() {
        return orgcoltype;
    }

    public void setOrgcoltype(String orgcoltype) {
        this.orgcoltype = orgcoltype;
    }

    public String getOrgunit() {
        return orgunit;
    }

    public void setOrgunit(String orgunit) {
        this.orgunit = orgunit;
    }

    public String getOrgsum() {
        return orgsum;
    }

    public void setOrgsum(String orgsum) {
        this.orgsum = orgsum;
    }

    public String getOrgcolvalue() {
        return orgcolvalue;
    }

    public void setOrgcolvalue(String orgcolvalue) {
        this.orgcolvalue = orgcolvalue;
    }


    public String getOrgkey() {
        return orgkey;
    }

    public void setOrgkey(String orgkey) {
        this.orgkey = orgkey;
    }

    public static ListCtrl parseText(String text) {
        ListCtrl t = CommonUtils.parseInputAttrsT(text, ListCtrl.class);

        return t;
    }

    @Override
    public String getView(String value) {
        // TODO Auto-generated method stub

        JSONObject jsonValue = null;
        try {
            if (value != null && value.trim().length() > 1)
                jsonValue = JSON.parseObject(value);
        } catch (Exception e) {
            System.out.println(e);
            jsonValue = null;
        }
        return parseListCtrl(this, 2, jsonValue);
    }

    @Override
    public String getEdit(String value) {
        JSONObject jsonValue = null;
        try {
            if (value != null && value.trim().length() > 1)
                jsonValue = JSON.parseObject(value);
        } catch (Exception e) {
            System.out.println(e);
            jsonValue = null;
        }


        return parseListCtrl(this, 1, jsonValue);
    }


    /**
     * 解析listctrl
     * 2018-01-25 listctrl对单位和默认值的拼装
     *
     * @param listCtrilData ueform中 data里的listctrl 整个json
     * @param type          1-编辑模式 2-纯浏览模式 采用parse替换
     * @param value         填充的值,可以为空
     * @return
     */
    private String parseListCtrl(ListCtrl lc, int type, JSONObject listCtrlValue) {

        String name = lc.getName();

        String temp_html = type != 1 ? "" : "<script> " +
                "function tbAddRow(dname) {\n" +
                "        var sTbid = dname + \"_table\";\n" +
                "        var trNum = $(\"#\" + sTbid + \" tbody\").find(\"tr\").length;\n" +
                "\n" +
                "        $(\"#\" + sTbid + \" .template\")\n" +
                "        //连同事件一起复制\n" +
                "            .clone(true)\n" +
                "            //去除模板标记\n" +
                "            .removeClass(\"template\").addClass(\"dataRow\" + trNum)\n" +
                "        //修改内部元素\n" +
                "            .find(\".delrow\").show().end()\n" +
                "            .find(\"input\").val(\"\").end()\n" +
                "            .find(\"textarea\").val(\"\").end()\n" +
                "        //插入表格\n" +
                "            .appendTo($(\"#\" + sTbid));\n" +
                "        // 处理一下name的值\n" +
                "        var lls = $(\".dataRow\" + trNum).find(\"input\");\n" +
                "        $.each(lls, function (index, value) {\n" +
                "            var oldName = $(lls[index]).attr(\"name\");\n" +
                "            $(lls[index]).attr(\"name\", oldName + \"[\" + trNum + \"]\");\n" +
                "        })\n" +
                "    }";

        temp_html += "function sum_total(dname, key) {\n" +
                "        var tsum = 0;\n" +
                "\n" +
                "        var tableId = dname + \"_table\";\n" +
                "        var inputs = $(\"#\" + tableId).find(\"input\");\n" +
                "        $.each(inputs, function (index, value) {\n" +
                "            var name = $(value).attr(\"name\");\n" +
                "            //console.log(name + \">>>>\" + name.indexOf(key));\n" +
                "            if (name != undefined && name.indexOf(key) != -1 && name.indexOf(\"total\") == -1) {\n" +
                "                var t = parseFloat($(value).val());\n" +
                "                if (!t) t = 0;\n" +
                "                if (t) tsum += t * 100;\n" +
                "                $(value).val(t);\n" +
                "            }\n" +
                "        });\n" +
                "\n" +
                "        tsum = tsum / 100;\n" +
                "        var xsd = tsum.toString().split(\".\");\n" +
                "        if (xsd.length == 1) {\n" +
                "            tsum = tsum.toString() + \".00\";\n" +
                "\n" +
                "        }\n" +
                "        if (xsd.length > 1) {\n" +
                "            if (xsd[1].length < 2) {\n" +
                "                tsum = tsum.toString() + \"0\";\n" +
                "            }\n" +
                "        }\n" +
                "        $('input[name=\"' + dname + \"_\" + key + '[total]\"]').val(tsum);\n" +
                "    }";
        temp_html += "</script>";


        String[] fields = lc.getOrgtitle().split("`");
        String[] filed_keys = lc.getOrgkey().split("`");
        String[] orgsums = lc.getOrgsum().split("`");

        String table = "<table id=\"" + name + "_table\" cellspacing=\"0\" class=\"table table-bordered table-condensed\" style=\"width: 100%;\"><thead>";
        table += " <tr><th colspan=\"" + fields.length + "\">" + getTitle();
        if (type == 1) {
            table += "<span class=\"pull-right\"> " +
                    "<button class=\"btn btn-small btn-success\" type=\"button\" onclick=\"tbAddRow('" + name + "')\">添加一行</button>" +
                    "</span> ";
        }


        table += "</th></tr><tr>";
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] != null && fields[i].length() > 0)
                table += "<th>" + fields[i] + "</th>";
        }
        table += "</tr></thead>";
        //template

        // 判断模式
        // JSONObject  = value != null ? JSON.parseObject(value) : null;

        if (type == 2) {
            //纯查看模式
            if (listCtrlValue != null) {
                Set<String> keys = listCtrlValue.keySet();
                for (String key : keys) {

                    JSONObject _key_v = listCtrlValue.getJSONObject(key);
                    table += " <tr class=\"template\">";
                    //Set<String> ts = _key_v.keySet();
                    for (int i = 0; i < fields.length && i < filed_keys.length && i < _key_v.size(); i++) {
                        if (fields[i] != null && fields[i].length() > 0)
                            table += "<td>" + _key_v.getString(filed_keys[i]) + "</td>";
                    }
                    table += " </tr>";
                }
            }

        } else {
            table += " <tr class=\"template\">";

            if (listCtrlValue != null) {
                Set<String> keys = listCtrlValue.keySet();
                int index = 0;
                for (String key : keys) {

                    JSONObject _key_v = listCtrlValue.getJSONObject(key);
                    table += " <tr class=\"template\">";
                    //Set<String> ts = _key_v.keySet();
                    for (int i = 0; i < fields.length && i < filed_keys.length && i < _key_v.size(); i++) {
                        if (filed_keys[i] != null && filed_keys[i].length() > 0)
                            table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[" + index + "][" + filed_keys[i] + "]\" value=\"" + _key_v.getString(filed_keys[i]) + "\"> </td>";

                    }
                    table += "<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow show\">删除</a></td>";

                    table += " </tr>";

                    index++;
                }
            } else {
                for (int i = 0; i < fields.length && i < filed_keys.length; i++) {
                    if (filed_keys[i] != null && filed_keys[i].length() > 0) {
                        if ("1".equals(orgsums[i])) { // 这列需要合计
                            table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[0][" + filed_keys[i] + "]\" value=\"\" onblur=\"sum_total('" + name + "','" +filed_keys[i] + "')\"> </td>";
                        }else {
                            table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[0][" + filed_keys[i] + "]\" value=\"\"> </td>";
                        }


                    }

                }

                table += "<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow hide\">删除</a></td>";
                table += " </tr>";
            }


            int size = listCtrlValue != null ? listCtrlValue.size() : 0;

            table += "<input type=\"hidden\" id='" + name + "_length' value='" + size + "' >";
            table += "<input type=\"hidden\" id='" + name + "_field' value='" + lc.getOrgkey() + "' >";
        }


        table += " </tbody>";

        // 判断是否需要合计
        table += "<tfooter><tr>";
        for (int i = 0; i < orgsums.length; i++) {
            if ("1".equals(orgsums[i])) {
                Integer ii = Integer.valueOf(i) + 1;
                table += "<td>合计：<input class=\"input-small\" name=\"" + name + "_" + filed_keys[i] + "[total]\" onblur=\"sum_total('data_5[4][]')\" readonly type=\"text\" value=\"\"></td>";
            } else {
                table += "<td></td>";
            }
        }
        table += "<td></td>";
        table += "</tr></tfooter>";
        table += "</table>";
        String content = temp_html + "<p>" + table + "</p>";
        return content;

    }

}
