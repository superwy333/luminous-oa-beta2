package cn.luminous.squab.constant;


/**
 *  常量
 */
public class Constant {

    /**
     * 任务状态
     */
    public static class TASK_STATES {

        public static final String IN_PROCESS = "0"; // 流转中
        public static final String PASSED = "1"; // 通过
        public static final String REJECTED = "2"; // 拒绝
    }

    /**
     * 业务键
     */
    public static class BIZ_KEY {
        public static final String PASS = "pass"; // 审批通过
        public static final String REJECT = "reject"; // 审批驳回

    }


}
