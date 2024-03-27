package com.xht.oauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.xht.oauth.mapper")
@ComponentScan(basePackages = "com.xht")
public class XhtOauthApplication {

	public static void main(String[] args) {

		SpringApplication.run(XhtOauthApplication.class, args);
	}


}
