package com.xht.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.xht.common.log.mapper"})
//@MapperScan("com.xht") //service 会 not found
@ComponentScan(value = "com.xht")
public class XhtGatewayApplication {

    public static void main(String[] args) {
//        RouteDefinitionRouteLocator
//        PathRoutePredicateFactory
//        RewritePathGatewayFilterFactory
//        DiscoveryClientRouteDefinitionLocator
        SpringApplication.run(XhtGatewayApplication.class, args);
    }

}
