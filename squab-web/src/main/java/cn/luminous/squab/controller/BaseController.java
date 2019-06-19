package cn.luminous.squab.controller;

import cn.luminous.squab.entity.http.Rq;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础controller
 */
public class BaseController {

    /**
     * 根据入参组装用于列表页查询的条件map
     * @param rq
     * @return
     * @throws Exception
     */
    protected Map<String, Object> parseListQueryCondition(Rq rq) throws Exception {
        Map<String, Object> condition = new HashMap<>();
        if (rq.getPage() != null) {
            condition.put("page", (rq.getPage() - 1) * rq.getLimit());
            condition.put("limit", rq.getLimit());
        }
        Map<String, String> data = (Map<String, String>) rq.getData();
        if (data != null) {
            condition.putAll(data);
        }
        return condition;
    }



}
