package cn.luminous.squab;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.luminous.squab.constant.Constant;
import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.service.ActivitiService;
import cn.luminous.squab.service.OaTaskService;
import cn.luminous.squab.service.SysUserService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkflowTest {
    @Autowired
    private OaTaskService oaTaskService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ActivitiService activitiService;


    /**
     * 测试发起请假流程
     */
    @Test
    public void testQJApply() throws Exception {
        List<SysUer> sysUerList = sysUserService.query(new SysUer());

        sysUerList.stream().forEach(sysUer -> {
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(sysUer.getName(), "123456");
                Subject subject = SecurityUtils.getSubject();
                subject.login(token);
                qjTestDetail(sysUer.getName());
                subject.logout();
            }catch (Exception e) {
                e.printStackTrace();
            }
        });




    }

    /**
     * 发起流程的测试
     * @param userName
     * @throws Exception
     */
    private void qjTestDetail(String userName) throws Exception {

        // 组装OaTask
        OaTask oaTask = new OaTask();
        oaTask.setBizKey("qjApply");
        oaTask.setData("[{\"name\":\"lsh\",\"value\":\"\"},{\"name\":\"sqr\",\"value\":\"王杨\"},{\"name\":\"bm\",\"value\":\"信息管理科\"},{\"name\":\"gw\",\"value\":\"二级职员\"},{\"name\":\"data_21\",\"value\":\"事假\"},{\"name\":\"sqri\",\"value\":\"20190521\"},{\"name\":\"data_47\",\"value\":\"调休\"},{\"name\":\"data_48\",\"value\":\"12345678\"},{\"name\":\"start\",\"value\":\"20190606\"},{\"name\":\"end\",\"value\":\"20190608\"},{\"name\":\"days\",\"value\":\"2\"},{\"name\":\"qjsm\",\"value\":\"科目三考试\"}]");
        oaTask.setProcessKey("qjApply");
        //oaTask.setApplyCode(userCode);
        oaTask.setTaskNo(oaTaskService.getTaskNo());
        oaTask.setApplyTime(new Date());
        //oaTaskService.registerTask(oaTask);
        String processKey = oaTask.getProcessKey();
        Map<String, Object> variables = parseJson(oaTask.getData());

        sysUserService.parseVariables("王杨", variables, Constant.PARSE_USERINFO_TYPE.APPLY);
        ProcessInstance processInstance = activitiService.startProcess(processKey, variables);
        oaTask.setProcInstId(processInstance.getProcessInstanceId());
        oaTask.setProcDefId(processInstance.getProcessDefinitionId());
        oaTask.setApplyName((String) variables.get("sqr"));
        oaTask.setTaskState(Constant.TASK_STATES.IN_PROCESS);

        // 记录提交的表单数据
        oaTaskService.add(oaTask);
    }



    private Map<String, Object> parseJson(String string) throws Exception {
        //JSONArray jsonArrayTotal = new JSONArray();
        Map<String, Object> variables = new HashMap<>();


        JSONArray jsonArray = JSONUtil.parseArray(string);
        for (Object object : jsonArray) {
            JSONObject jsonObject = JSONUtil.parseObj(object);
            //JSONObject jsonObjectNew = new JSONObject();
            //jsonObjectNew.put((String) jsonObject.get("name"), jsonObject.get("value"));
            //jsonArrayTotal.add(jsonObjectNew);
            variables.put((String) jsonObject.get("name"), jsonObject.get("value"));
        }
        return variables;

    }




}
