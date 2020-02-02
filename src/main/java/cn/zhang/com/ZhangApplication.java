package cn.zhang.com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "cn.zhang.com.mapper")
@EnableScheduling
public class ZhangApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhangApplication.class, args);
    }

}
