package cn.luminous.squab.entity.http;

import java.util.Map;

public class Rq{

    /**
     * 业务键
     */
    private String bizKey;

    /**
     * 前端传过来的json数据
     */
    private Map<String,Object> data;

    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
