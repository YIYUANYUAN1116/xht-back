package com.xht.oauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.xht.oauth.mapper","com.xht.common.log.mapper"})
//@MapperScan("com.xht") //service 会 not found
@ComponentScan(basePackages = "com.xht")
public class XhtOauthApplication {

	public static void main(String[] args) {

		SpringApplication.run(XhtOauthApplication.class, args);
	}


}
