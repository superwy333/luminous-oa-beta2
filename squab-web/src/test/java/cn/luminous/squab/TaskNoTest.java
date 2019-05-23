package cn.luminous.squab;


import cn.luminous.squab.service.OaTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskNoTest {

    @Autowired
    OaTaskService oaTaskService;


    @Test
    public void test1() throws Exception {
        String taskNo = oaTaskService.getTaskNo();
        System.out.println(taskNo);
    }

    @Test
    public void test2() throws Exception {

        new Thread(() -> {
            while (true) {
                System.out.println("aaa");
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                }catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                System.out.println("bbb");
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                }catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();

    }

    @Test
    public void test3() throws Exception {
        while (true) {
            System.out.println("aaa");
        }

    }


    @Test
    public void testt() {

        Thread[] ths = new Thread[100];

        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + ">>>>>>>" + oaTaskService.getTaskNo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "myThread-" + i);
        }

        Arrays.asList(ths).forEach(t -> t.start());

    }


}
