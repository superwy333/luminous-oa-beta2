package cn.luminous.squab.mockMvc;

import cn.luminous.squab.entity.http.Rq;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class LoginTest {

    @Autowired
    ApplicationContext ioc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SecurityManager securityManager;
    protected MockHttpSession mockHttpSession;
    protected MockHttpServletRequest mockHttpServletRequest;
    protected MockHttpServletResponse mockHttpServletResponse;
    private MockMvc mockMvc;
    public Subject subject;

    @Before
    public void setUp() throws Exception {
        Filter shiroFilter = (Filter) ioc.getBean(AbstractShiroFilter.class);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(shiroFilter).build();//建议使用这种
        mockHttpServletRequest = new MockHttpServletRequest(webApplicationContext.getServletContext());
        mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpSession = new MockHttpSession(webApplicationContext.getServletContext());
        mockHttpServletRequest.setSession(mockHttpSession);
        SecurityUtils.setSecurityManager(securityManager);
        subject = new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse).buildSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("王杨", "123456");
        token.setHost("127.0.0.1");
        subject.login(token);



    }

    @Test
    public void getHello() {
        try {
            //login("王杨","123456");
            //taskToDo();
            SecurityManager securityManager = (SecurityManager) ioc.getBean("securityManager");
            SecurityUtils.setSecurityManager(securityManager);
            UsernamePasswordToken token = new UsernamePasswordToken("王杨", "123456");
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getHello2() throws Exception {
        taskToDo();


    }


    private void login(String username, String password) throws Exception {
        /**
         * 1、mockMvc.perform执行一个请求。
         * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
         * 3、ResultActions.param添加请求传值
         * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
         * 5、ResultActions.andExpect添加执行完成后的断言。
         * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
         *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
         * 5、ResultActions.andReturn表示执行完成后返回相应的结果。
         */
        SecurityManager securityManager = (SecurityManager) ioc.getBean("securityManager");
        SecurityUtils.setSecurityManager(securityManager);

        MvcResult mvcResult = mockMvc.perform(post("/login")
                .param("username", username)
                .param("password", password)
                .accept(MediaType.TEXT_HTML_VALUE))
                // .andExpect(MockMvcResultMatchers.status().isOk())             //等同于Assert.assertEquals(200,status);
                // .andExpect(MockMvcResultMatchers.content().string("hello lvgang"))    //等同于 Assert.assertEquals("hello lvgang",content);
                .andDo(print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();                 //得到返回代码
        String content = mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(302, status);                        //断言，判断返回代码是否正确
        //Assert.assertEquals("hello lvgang", content);            //断言，判断返回的值是否正确


    }


    private void logout() {
        SecurityManager securityManager = (SecurityManager) ioc.getBean("securityManager");
        SecurityUtils.setSecurityManager(securityManager);

    }


    private void taskToDo() throws Exception {
        Rq rq = new Rq();
        rq.setLimit(1);
        rq.setPage(100);
        String requestJson = JSONObject.toJSONString(rq);

        String responseString = mockMvc.perform(post("/workflow/taskToDo")
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(responseString);


    }


}
