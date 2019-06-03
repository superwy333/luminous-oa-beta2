package cn.luminous.squab.mockMvc;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleShiroIntegrationTest extends AbstractShiroTest {

    @Autowired
    SecurityManager securityManager;

    @BeforeClass
    public static void beforeClass() {
        //0.  Build and set the SecurityManager used to build Subject instances used in your tests
        //    This typically only needs to be done once per class if your shiro.ini doesn't change,
        //    otherwise, you'll need to do this logic in each test that is different
        //Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:test.shiro.ini");
        //setSecurityManager(factory.getInstance());
        //setSecurityManager();
    }

    @Test
    public void testSimple() {
        //1.  Build the Subject instance for the test to run:
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();

        //2. Bind the subject to the current thread:
        setSubject(subjectUnderTest);

        //perform test logic here.  Any call to
        //SecurityUtils.getSubject() directly (or nested in the
        //call stack) will work properly.
    }

//    @AfterClass
//    public void tearDownSubject() {
//        //3. Unbind the subject from the current thread:
//        clearSubject();
//    }


}
