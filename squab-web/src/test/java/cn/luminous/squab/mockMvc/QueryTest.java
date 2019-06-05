package cn.luminous.squab.mockMvc;

import cn.luminous.squab.entity.OaTask;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.service.SysUserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class QueryTest extends BaseMock {

    @Autowired
    private SysUserService sysUserService;


    @Test
    public void test() throws Exception {

//        List<SysUer> sysUerList = sysUserService.query(new SysUer());
//        sysUerList.stream().forEach(sysUer -> {
//            try {
//                login(sysUer.getName(), "123456");
//                queryTaskTodo();
//
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        });
        login("徐炜", "123456");
        queryTaskTodo();


    }

    private void queryTaskTodo() throws Exception {
        Rq rq = new Rq();
        //rq.setLimit(1);
        //rq.setPage(100);
        String requestJson = JSONObject.toJSONString(rq);

        String responseString = mockMvc.perform(post("/workflow/taskToDo")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONObject rseponse = JSONObject.parseObject(responseString);
        System.out.println(responseString);

    }


}
