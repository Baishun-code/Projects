package com.psp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.psp.mapper")
@EnableEurekaClient
@EnableAspectJAutoProxy(exposeProxy = true)
public class PspEntityOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(PspEntityOneApplication.class, args);
    }
}
