package cn.luminous.squab.constant;


/**
 * 常量
 */
public class Constant {

    /**
     * 任务状态
     */
    public static class TASK_STATES {
        public static final String IN_PROCESS = "0"; // 流转中
        public static final String PASSED = "1"; // 通过
        public static final String REJECTED = "2"; // 拒绝
        public static final String CANCEL = "3"; // 撤回
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
        public static final String CANCEL = "2"; // 驳回

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

        /**
         * 财务
         */
        public static final String CW = "cw";

        /**
         * 出纳
         */
        public static final String CN = "cn";

        /**
         * 仓库汇总
         */
        public static final String CKHZ = "ckhz";
        /**
         * 仓库初审核
         */
        public static final String CGCSH = "cgcsh";
        /**
         * 采购管理部
         */
        public static final String CGGLB = "cgglb";
        /**
         * 仓库发货
         */
        public static final String CKFH = "ckfh";
    }

    /**
     * 数据字典相关常量
     */
    public static class SYS_DICT_CODE {

        /**
         * 流程有关的动态人员分配
         */
        public static final String DYNAMIC_APPROVER = "dynamic_approver";

    }

    public static class BIZ_MAPPING_AUTH_TYPE {
        /**
         * 所有
         */
        public static final String ALL = "0";

        /**
         * 指定人
         */
        public static final String BY_USER = "1";
        /**
         * 指定部门
         */
        public static final String BY_DEPT = "2";
        /**
         * 指定岗位
         */
        public static final String BY_POST = "3";
    }

    /**
     * 使用用户姓名登陆还是工号登陆
     */
    public static class LOGIN_TYPE {
        /**
         * 用户姓名
         */
        public static final String USER_NAME = "0";
        /**
         * 工号
         */
        public static final String USER_CODE = "1";
    }

    /**
     * 组装用户信息的类型
     */
    public static class PARSE_USERINFO_TYPE {

        /**
         * 申请时组装
         */
        public static final String APPLY = "apply";
        /**
         * 审批时组装
         */
        public static final String APPROVE = "approve";

    }

    /**
     * 任务详情查看类型
     */
    public static class TASK_DETAIL_TYPE {
        /**
         * 详情
         */
        public static final String DETAIL = "1";
        /**
         * 编辑
         */
        public static final String EDIT = "2";
        /**
         * 打印
         */
        public static final String PRINT = "3";
    }


}
