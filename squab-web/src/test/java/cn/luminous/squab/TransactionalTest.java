package cn.luminous.squab;


import cn.luminous.squab.service.OaTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalTest {

    @Autowired
    OaTaskService oaTaskService;



    @Test
    public void test() throws Exception{
        oaTaskService.testTransactional();
    }


}
