package cn.luminous.squab.entity.http;


import java.util.HashMap;

/**
 * 和前端交互的返回体
 */
public class R extends HashMap<String,Object> {

    private static final String CODE = "code";
    private static final String MSG = "msg";
    private static final String SUCCESS = "success";
    private static final String DATA = "data";
    private static final Integer SUCCESS_CODE = 0;
    private static final Integer FAIL_CODE = 1; // 默认错误code
    private static final Boolean SUCCESS_FLAG = true;
    private static final Boolean FAIL_FLAG = false;

    public static R ok() {
        return ok(null);
    }

    public static R ok(Object data) {
        R r = new R();
        r.put(CODE,SUCCESS_CODE);
        r.put(MSG,null);
        r.put(SUCCESS,SUCCESS_FLAG);
        r.put(DATA,data);
        return r;
    }

    public static R nok() {
        return nok(null,FAIL_CODE,null);
    }

    public static R nok(String msg) {
        return nok(null,FAIL_CODE,msg);
    }

    public static R nok(Object data, Integer failCode, String msg) {
        R r = new R();
        r.put(CODE,FAIL_CODE);
        r.put(MSG,msg);
        r.put(SUCCESS,FAIL_FLAG);
        r.put(DATA,data);
        return r;
    }

}
