package com.xht.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
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
