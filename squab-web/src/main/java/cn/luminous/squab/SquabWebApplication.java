package cn.luminous.squab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"cn.luminous.squab.mapper"})
public class SquabWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SquabWebApplication.class, args);
    }

}
