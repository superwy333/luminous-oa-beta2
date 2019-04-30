package cn.luminous.squab.entity.http;

import cn.hutool.json.JSONUtil;

public class Rq{

    /**
     * 业务键
     */
    private String bizKey;

    /**
     * 前端传过来的json数据
     */
    private Object data;

    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
