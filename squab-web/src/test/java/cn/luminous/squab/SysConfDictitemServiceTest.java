package cn.luminous.squab;


import cn.luminous.squab.entity.SysConfDictitem;
import cn.luminous.squab.service.SysConfDictitemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysConfDictitemServiceTest {


    @Autowired
    private SysConfDictitemService sysConfDictitemService;


    @Test
    public void test1() {
        //System.out.println(sysConfDictitemService);
        List<SysConfDictitem> sysConfDictitemList = sysConfDictitemService.query(new SysConfDictitem());
        sysConfDictitemList.stream().forEach(sysConfDictitem -> {
            System.out.println(sysConfDictitem.getDicItemCode());
        });

    }

}
