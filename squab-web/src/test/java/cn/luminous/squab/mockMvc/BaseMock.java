package cn.luminous.squab.mockMvc;

import cn.luminous.squab.entity.http.Rq;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.subject.WebSubject;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseMock {
    @Autowired
    ApplicationContext ioc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SecurityManager securityManager;
    protected MockHttpSession mockHttpSession;
    protected MockHttpServletRequest mockHttpServletRequest;
    protected MockHttpServletResponse mockHttpServletResponse;
    protected MockMvc mockMvc;
    public Subject subject;

    @Before
    public void setUp() {
        Filter shiroFilter = (Filter) ioc.getBean(AbstractShiroFilter.class);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(shiroFilter).build();
        mockHttpServletRequest = new MockHttpServletRequest(webApplicationContext.getServletContext());
        mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpSession = new MockHttpSession(webApplicationContext.getServletContext());
        mockHttpServletRequest.setSession(mockHttpSession);
        SecurityUtils.setSecurityManager(securityManager);
        subject = new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse).buildSubject();
    }

    protected void login(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setHost("127.0.0.1");
        subject.login(token);

    }


//    @Test
//    public void test() throws Exception {
//
//        login();
//
//        Rq rq = new Rq();
//        rq.setLimit(1);
//        rq.setPage(100);
//        String requestJson = JSONObject.toJSONString(rq);
//
//        String responseString = mockMvc.perform(post("/workflow/taskToDo")
//                .session(mockHttpSession)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJson))
//                .andDo(print())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        System.out.println(responseString);
//
//
//    }


}
