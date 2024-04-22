package com.xht.activiti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : YIYUANYUAN
 * @date: 2024/4/22  22:42
 */
@SpringBootApplication
@MapperScan("com.xht")
@ComponentScan(value = "com.xht")
public class XhtActivitiApplication {
    public static void main(String[] args) {
        SpringApplication.run(XhtActivitiApplication.class, args);
    }
}
