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
@MapperScan(basePackages = {"com.xht.activiti.mapper","com.xht.security,common.log.mapper"})
//@MapperScan("com.xht") //service 会 not found
@ComponentScan(value = "com.xht")
public class XhtActivitiApplication {
    public static void main(String[] args) {
        SpringApplication.run(XhtActivitiApplication.class, args);
    }
}
