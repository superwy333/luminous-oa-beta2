package cn.luminous.squab;

import cn.luminous.squab.service.ActivitiService;
import cn.luminous.squab.util.SpringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IocTest {


    @Autowired
    ApplicationContext ioc;


    @Test
    public void test() {
        System.out.println(ioc.containsBean("stencilsetRestResource"));
        System.out.println(ioc.containsBean("modelEditorJsonRestResource"));
        System.out.println(ioc.containsBean("modelSaveRestResource"));
    }

    @Test
    public void test2() {

        ActivitiService activitiService = (ActivitiService) SpringUtil.getObject("activitiService");
        System.out.println(activitiService);
    }



}
