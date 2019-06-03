package cn.luminous.squab.mockMvc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Test;
import static org.easymock.EasyMock.*;

public class ExampleShiroUnitTest extends AbstractShiroTest {

    @Test
    public void testSimple() {

        //1.  Create a mock authenticated Subject instance for the test to run:
        Subject subjectUnderTest = createNiceMock(Subject.class);
        expect(subjectUnderTest.isAuthenticated()).andReturn(true);

        //2. Bind the subject to the current thread:
        setSubject(subjectUnderTest);

        //perform test logic here.  Any call to
        //SecurityUtils.getSubject() directly (or nested in the
        //call stack) will work properly.
        UsernamePasswordToken token = new UsernamePasswordToken("王杨", "123456");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        System.out.println("userName>>>>>>>>>>>> " + userName);
    }

    @After
    public void tearDownSubject() {
        //3. Unbind the subject from the current thread:
        clearSubject();
    }
}
