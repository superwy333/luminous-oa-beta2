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
        public static final String PASS = "approvePass"; // 审批通过
        public static final String REJECT = "approveReject"; // 审批驳回

    }

    /**
     * 审批结果
     */
    public static class TASK_APPROVE_RESULT {
        public static final String PASS = "0"; // 同意
        public static final String REJECT = "1"; // 驳回

    }

    public static class ASSIGNEE_KEY {
        /**
         * 科室
         */
        public static final String KS = "ks";
        /**
         * 上级部门
         */
        public static final String SJBM = "sjbm";
        /**
         * 分管领导
         */
        public static final String FGLD = "fgld";
        /**
         * 总经理
         */
        public static final String ZJL = "zjl";
        /**
         * 人事备案
         */
        public static final String RSBA = "rsba";
    }


}
