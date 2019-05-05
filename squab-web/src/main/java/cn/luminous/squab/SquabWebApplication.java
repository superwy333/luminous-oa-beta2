package cn.luminous.squab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"org.activiti.rest.editor","cn.luminous"})
@MapperScan(basePackages = {"cn.luminous.squab.mapper"})
//@ComponentScan({"org.activiti.rest.editor"})
public class SquabWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SquabWebApplication.class, args);
    }

}
