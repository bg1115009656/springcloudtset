package com.gbai.gatewayservice;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServiceApplication implements ApplicationContextAware {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    ApplicationContext applicationContext;
    @Bean
    public RouteLocator locator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p -> p.path("/baidu")
                            .uri("http://baidu.com:80/"))
                .build();

    }
    @Bean
    public RouteLocatorBuilder builder(){
        return  new RouteLocatorBuilder((ConfigurableApplicationContext) applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
