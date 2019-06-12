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

    /**
     * 分页参数
     */
    private Integer page;

    private Integer limit;

    private Long oaTaskId;

    public Long getOaTaskId() {
        return oaTaskId;
    }

    public void setOaTaskId(Long oaTaskId) {
        this.oaTaskId = oaTaskId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

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
