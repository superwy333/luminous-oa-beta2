package cn.luminous.squab;

import cn.luminous.squab.entity.Department;
import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.model.OaTaskModel;
import cn.luminous.squab.service.DepartmentService;
import cn.luminous.squab.service.OaTaskService;
import cn.luminous.squab.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TkMabatisTest {


    @Autowired
    SysUserService sysUserService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    OaTaskService oaTaskService;
    @Autowired
    ApplicationContext ioc;




    @Test
    public void test1() {
        log.info("----------->" + ioc.containsBean("sysUserService"));
        SysUer sysUer = new SysUer();
        List<SysUer> sysUerList = sysUserService.query(sysUer);
        log.info("---result---" + sysUerList.size());
    }

    @Test
    public void testAdd() {
        SysUer sysUer = new SysUer();
        sysUer.setName("Lisa");
        sysUer.setPassword("666");
        int count = sysUserService.add(sysUer); // deleted默认给了0，version为空
        log.info("---result---" + count);
    }

    @Test
    public void testAddSelective() {
        SysUer sysUer = new SysUer();
        sysUer.setName("Lisa");
        sysUer.setPassword("666");
        int count = sysUserService.addSelective(sysUer); // deleted默认给了0，version为空
        log.info("---result---" + count);
    }

    @Test
    public void testUpdateSelective() {
        SysUer sysUer = sysUserService.queryById(1L);
        sysUer.setPassword("888");
        sysUer.setName(null);
        int count = sysUserService.updateByIdSelective(sysUer);
        log.info("---result---" + count);
    }

    @Test
    public void testUpdate() {
        SysUer sysUer = sysUserService.queryById(1L);
        sysUer.setPassword("888");
        sysUer.setName(null);
        int count = sysUserService.updateById(sysUer);
        log.info("---result---" + count);
    }

    @Test
    public void testSelect() {
        SysUer sysUer = new SysUer();
        sysUer.setName("Lisa");
        List<SysUer> sysUerList = sysUserService.query(sysUer);
        log.info("---result---" + sysUerList.size());

    }

    @Test
    public void testSelectOne() {
        SysUer sysUer = new SysUer();
        sysUer.setName("Lisa");
        SysUer sysUer2 = sysUserService.queryOne(sysUer);
        log.info("---result---" + sysUer2);

    }

    @Test
    public void testQueryByXml() {
        SysUer sysUer = new SysUer();
        sysUer.setId(2L);
        List<SysUer> sysUerList = sysUserService.queryAllUsers(sysUer);
        log.info("---result---" + sysUerList.size());

    }



    @Test
    public void test333() throws Exception {
        //List<SysUer> sysUerList = sysUserService.queryAllUsers(new SysUer());
        Department department = departmentService.queryDepartment("tt");
        //List<OaTaskModel> oaTaskModelList = oaTaskService.queryMyTask("tt");
        System.out.println("aaa");

    }

    @Test
    public void test444() throws Exception {
        //List<SysUer> sysUerList = sysUserService.queryAllUsers(new SysUer());
        //Department department = departmentService.queryDepartment("tt");
        //List<OaTaskModel> oaTaskModelList = oaTaskService.queryMyTask("tt");
        System.out.println("aaa");

    }



}
